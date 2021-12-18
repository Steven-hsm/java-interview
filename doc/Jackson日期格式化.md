最近公司项目遇到一个时间格式转换问题，项目调用时包含了Date类型的时间返回值，报了一下错误
```shell
Cannot parse date "2021-11-19 11:59:39": while it seems to fit format 'yyyy-MM-dd'T'HH:mm:ss.SSSZ', parsing fails (leniency? null))
```
网上一般说加上以下配置
```yaml
spring:
  jackson:
    date-format: yyyy-MM-dd HH:mm:ss
    time-zone: GMT+8 #时差问题
    serialization:
      write_dates_as_timestamps: false
```

但是无奈项目四五十个，都是线上项目，加起来优点麻烦（有得项目加，有得项目没加估计问题会更严重）

另外一种解决方案就是在字段上增加一下注解

```java
@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
```

这里也有一个问题，如果有得老项目格式不是这个格式呢，时间转换岂不是又要出问题

最终想到的办法是实现jackson日期的反序列化方法，对所有可能的日期格式做解析

```java
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import org.apache.commons.lang3.StringUtils;
import org.springframework.boot.jackson.JsonComponent;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

@JsonComponent
public class DateFormatConfig {
    private static final SimpleDateFormat DEFAULT_DATE_FORMAT = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    private static final SimpleDateFormat SSSZ_DATE_FORMAT = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSZ");
    /**
     * 日期序列化
     */
    public static class DateJsonSerializer extends JsonSerializer<Date> {
        @Override
        public void serialize(Date date, JsonGenerator jsonGenerator, SerializerProvider serializerProvider) throws IOException {
            if(date != null){
                jsonGenerator.writeString(DEFAULT_DATE_FORMAT.format(date));
            }
        }
    }
    /**
     * 日期反序列化
     */
    public static class DateJsonDeserializer extends JsonDeserializer<Date> {
        @Override
        public Date deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException, JsonProcessingException {
            try {
                String text = jsonParser.getText();
                if(StringUtils.isEmpty(text)){
                    return null;
                }
                //时间戳格式
                if(text.length() == 13 || text.length() ==10){
                    return DEFAULT_DATE_FORMAT.parse(DEFAULT_DATE_FORMAT.format(Long.parseLong(text)));
                }
                //yyyy-MM-dd'T'HH:mm:ss.SSSZ
               if(text.contains("T") && text.contains("SSSZ")){
                    return SSSZ_DATE_FORMAT.parse(text);
                }
                //DEFAULT_DATE_FORMAT
                return DEFAULT_DATE_FORMAT.parse(text);
            } catch (ParseException e) {
                throw new RuntimeException(e);
            }
        }
    }
}
```



