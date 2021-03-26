### 1.查看全局信息

```shell
GET / 			# 查看Elasticsearch信息

PUT _cluster/settings
{JSON_data}				#设置集群信息
```

### 2.文档索引创建更新

```shell
PUT {index_name}/_doc/{id}
{JSON_data}				# 创建一个索引文档


```

