package com.hsm.elasticsearch.entity;

import lombok.Data;
import org.zxp.esclientrhl.annotation.ESID;
import org.zxp.esclientrhl.annotation.ESMapping;
import org.zxp.esclientrhl.annotation.ESMetaData;
import org.zxp.esclientrhl.enums.DataType;

/**
 * @Classname Main2
 * @Description TODO
 * @Date 2021/5/14 20:45
 * @Created by huangsm
 */
@ESMetaData(indexName = "main2", number_of_shards = 5, number_of_replicas = 0)
@Data
public class Main2 {
    @ESID
    private String proposal_no;
    @ESMapping(datatype = DataType.text_type)
    private String business_nature_name;
    @ESMapping(datatype = DataType.text_type)
    private String appli_name;
}
