Restful是一种面向资源的架构风格，可以简单理解为：使用URL定位资源，用HTTP 动词（GET,POST,DELETE,PUT）描述操作。

* GET查询 
* PUT添加
* POST修改
* DELE删除

### 1. 分词操作

**测试分词效果**

```json
POST _analyze
{
  "analyzer":"standard",
  "text":"我爱你中国"
}

POST _analyze
{
  "analyzer":"ik_smart",
  "text":"我爱你中国"
}

POST _analyze
{
  "analyzer":"ik_max_word",
  "text":"我爱你中国"
}
```

**指定索引全局分词器**

```json
PUT /school_index
{
  "settings":{
    "index" : {
      "analysis.analyzer.default.type": "ik_max_word"
    }
  }
}
```

### 2. 索引相关操作

**创建索引**

```http
PUT /test_index
```

**查询索引**

```http
GET /test_index
```

**删除索引**

```http
DELETE /test_index
```

### 3. 文档相关操作

**添加文档**

```http
PUT /test_index/_doc/1
{
  "name": "张三",
  "sex": 1,
  "age": 25,
  "address": "广州天河公园",
  "remark": "java developer"
}
```

**更新文档**

```http
PUT /test_index/_doc/1
{
  "name": "张三",
  "sex": 1,
  "age": 27,
  "address": "广州天河公园",
  "remark": "java developer"
}
```

**查询文档**

```http
GET /test_index/_doc/1
```

**删除文档**

```http
DELETE /test_index/_doc/1
```

**批量获取文档**

```http
GET _mget
{
  "docs":[
    {
       "_index": "test_index",
        "_type": "_doc",
        "_id": 1
    },
    {
       "_index": "test_index",
        "_type": "_doc",
        "_id": 2
    }
  ]
}

GET /test_index/_mget
{
  "docs":[
    {
        "_id": 1
    },
    {
        "_id": 2
    }
  ]
}
```

**批量操作文档数据**

1. 批量创建文档

   ```http
   POST _bulk
   {"create":{"_index":"article"}}
   {"id":3,"title":"白起老师1","content":"白起老师666","tags":["java", "面向对 象"],"create_time":1554015482530}
   {"create":{"_index":"article"}}
   {"id":4,"title":"白起老师2","content":"白起老师NB","tags":["java", "面向对 象"],"create_time":1554015482530}
   ```

2. 普通创建或者更新文档

   ```http
   POST _bulk
   {"index":{"_index":"article","_id":3}}
   {"id":3,"title":"白起老师1","content":"白起老师666","tags":["java", "面向对 象"],"create_time":1554015482530}
   {"index":{"_index":"article","_id":4}}
   {"id":4,"title":"白起老师2","content":"白起老师NB","tags":["java", "面向对 象"],"create_time":1554015482530}
   ```

3. 批量删除

   ```http
   POST _bulk
   {"delete":{"_index":"article","_id":3}}
   {"delete":{"_index":"article","_id":4}}
   
   ```

4. 批量修改

   ```http
   POST _bulk
   {"update":{"_index":"article","_id":3}}
   {"doc":{"title":"ES大法必修内功"}}
   {"update":{"_index":"article","_id":4}}
   {"doc":{"create_time":1554018421008}}
   ```

### 4. 查询相关操作

#### 4.1 类sql查询

基本查询方式为 `GET /es_db/_doc/_search?q=*** `，q后面为具体的查询语句

```http
GET /test_index/_doc/_search?q=age:28 => where age = 28 

GET /test_index/_doc/_search?q=age[25 TO 26] => where age between 25 and 26

GET /test_index/_doc/_mget
{
	"ids":["1","2"]
} => where age ids in("1","2")

GET /test_index/_doc/_search?q=age:<=28  => where age <= 28

GET /test_index/_doc/_search?q=age[25 TO 26]&from=0&size=1 =>where  age between 25 and 26 limit 0, 1

GET /test_index/_doc/_search?_source=name,age => select name,age from test_index	

GET /test_index/_doc/_search?sort=age:desc =>  order by age desc
```

#### 4.2 DSL高级查询

![](C:\Users\steven\AppData\Roaming\Typora\typora-user-images\image-20220219150746824.png)

DSL查询语言中存在两种：查询DSL（query DSL）和过滤DSL（filter DSL）

**查询DSL**:考虑文档是否匹配这个查询，相关度是否足够高。ES中索引的数据都会存储一个 _score分值，分值越高就代表越匹配												

* relevence																 	
* full text
* not cached
* slower

**filters**：考虑文档是否匹配

* boolean yes/no
* exact values
* cached
* faster

##### 4.2.1 term精准查询

不会对字段进行分词查询，会采用精确匹配。采用term查询，查询字段映射类型属于keyword

```http
POST /test_index/_doc/_search
{
  "query": {
    "term":{
      "name":"admin"
    }
  }
}
POST /test_index/_doc/_search
{
  "query": {
    "term":{
      "name.keyword":"admin" #如果本身的字段类型不是keyword，如text需要增加指定的keyword类型
    }
  }
}

"name": {
"type": "text",
	"fields": {
		"keyword": {
			"ignore_above": 256,
			"type": "keyword"
		}
	}
}
```

##### 4.2.2 match模糊查询

match会根据该字段的分词器，进行分词查询 

```http
POST /test_index/_doc/_search
{
  "query": {
    "match":{
      "address":"广州"
    }
  }
}
```

##### 4.2.3 多字段模糊查询

```http
POST /test_index/_doc/_search
{
  "query": {
    "multi_match":{
      "query":"广州",
      "fields":["address","name"]
    }
  }
}
```

##### 4.2.4 未指定字段条件查询 query_string 含 AND 与 OR 条件

```http
POST /test_index/_doc/_search
{
  "query": {
    "query_string":{
      "query":"(张三) OR 长沙"
    }
  }
}
```

##### 4.2.5 指定字段条件查询 query_string , 含 AND 与 OR 条件

```http
POST /test_index/_doc/_search
{
  "query": {
    "query_string":{
      "query":"(张三) OR 长沙",
      "fields":["name","address"]
    }
  }
}
```

##### 4.2.6 范围查询

* range：范围关键字
* gte 大于等于
* lte 小于等于 
* gt 大于 
* lt 小于 
* now 当前时间

```http
POST /test_index/_doc/_search
{
  "query": {
    "range":{
      "age":{
        "gte":24
      }
    }
  }
}
```

##### 4.2.7 分页、输出字段、排序综合查询 

```http
POST /test_index/_doc/_search
{
  "query": {
    "range":{
      "age":{
        "gte":24
      }
    }
  },
  "from": 0,
  "size": 2,
  "_source": ["name", "age", "book"],
  "sort": {"age":"desc"}
}
```

##### 4.2.8 过滤查询

Filter过滤器方式查询，它的查询不会计算相关性分值，也不会对结果进行排序, 因此效率会高一点，查询的结果可以被缓存

```http
POST /test_index/_doc/_search
{
  "query": {
    "bool":{
      "filter":{
        "term":{
          "age":25
        }
      }
    }
  },
  "from": 0,
  "size": 2,
  "_source": ["name", "age", "book"],
  "sort": {"age":"desc"}
}
```

#### 4.3 总结

* match：模糊匹配，需要指定字段名，但是输入会进行分词。match是一个部分匹配的模 糊查询。查询条件相对来说比较宽松。 
* term: 这种查询和match在有些时候是等价的。不会进行分词
* match_phase：会对输入做分词，但是需要结果中也包含所有的分词，而且顺序要求一样
* query_string：和match类似，但是match需要指定字段名，query_string是在所有字段中搜索，范围更广泛。 

### 5.聚合搜索

**bucket和metric概念简介**

* bucket就是一个聚合搜索时的数据分组
* metric就是对一个bucket数据执行的统计分析

1. group by

   ```http
   GET /test_index/_search
   {
       "aggs":{
           "group_by_color":{
               "terms":{  
                   "field" : "age",
                   "order": {
                     "_count":"desc"
                   }
               }
           }
       }
   }
   ```

2. avg

   ```http
   GET /test_index/_search
   {
     "aggs": {
       "group_by_age": {
         "terms": {
           "field": "age",
           "order": {
             "avg_by_age": "desc"
           }
         },
         "aggs": {
           "avg_by_age": {
             "avg": {
               "field": "age"
             }
           }
         }
       }
     }
   }
   ```

3. histogram 区间统计

   histogram类似terms，也是进行bucket分组操作的，是根据一个field，实现数据 区间分组。 

   ```http
   GET /test_index/_search
   {
     "aggs": {
       "histogram_by_price": {
         "histogram": {
           "field": "age",
           "interval": 1000000
         },
         "aggs": {
           "avg_by_age": {
             "avg": {
               "field": "age"
             }
           }
         }
       }
     }
   }
   ```

4. date_histogram区间分组 

   date_histogram可以对date类型的field执行区间聚合分组，如每月销量，每年销 量等。

   ```http
   GET /test_index/_search
   {
     "aggs": {
       "histogram_by_date": {
         "date_histogram": {
           "field": "sold_date",
           "interval": "month",
           "format": "yyyy‐MM‐dd",
           "min_doc_count": 1,
           "extended_bounds": {
             "min": "2021‐01‐01",
             "max": "2022‐12‐31"
           }
         },
         "aggs": {
           "sum_by_price": {
             "avg": {
               "field": "price"
             }
           }
         }
       }
     }
   }
   ```

5. 

