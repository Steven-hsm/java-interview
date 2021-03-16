package com.hsm.elasticsearch.entity;

import lombok.Data;
import org.zxp.esclientrhl.annotation.ESID;
import org.zxp.esclientrhl.annotation.ESMapping;
import org.zxp.esclientrhl.annotation.ESMetaData;
import org.zxp.esclientrhl.enums.DataType;
import org.zxp.esclientrhl.repository.GeoEntity;

@ESMetaData(indexName = "user", number_of_shards = 5, number_of_replicas = 0)
@Data
public class UserPO {
    @ESID
    private String userCode;

    @ESMapping(datatype = DataType.text_type)
    private String userName;

    @ESMapping(datatype = DataType.integer_type)
    private int age;

    @ESMapping(datatype = DataType.geo_point_type)
    GeoEntity geo;

    @ESMapping(datatype = DataType.nested_type,nested_class = ChinaNamePO.class)
    private ChinaNamePO chinaName;
}
