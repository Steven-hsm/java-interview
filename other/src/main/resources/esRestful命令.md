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



### 3. 文档查询

"_source": 用来指定获取的资源

"highlight":用来指定高亮的字段

#### 3.1 基本的匹配

**不指定具体的字段**

```shell
GET /bookdb_index/book/_search?q=guide # 查询任一字段包含guide的记录
==>
{
    "query": {
        "multi_match" : {
            "query" : "guide",
            "fields" : ["_all"]
        }
    }
}
```

**指定具体的字段**

```shell
GET /bookdb_index/book/_search?q=title:in action # 查询字段title中包含 in action的记录
==>
{
    "query": {
        "match" : {
            "title" : "in action"
        }
    },
    "size": 2,
    "from": 0,
    "_source": [ "title", "summary", "publish_date" ],
    "highlight": {
        "fields" : {
            "title" : {}
        }
    }
}

```

#### 3.2 多字段查询

```shell
POST /bookdb_index/book/_search
{
    "query": {
        "multi_match" : {
            "query" : "elasticsearch guide",
            "fields": ["title", "summary"]
        }
    }
}
```

#### 3.3 Boosting

```shell
POST /bookdb_index/book/_search
{
    "query": {
        "multi_match" : {
            "query" : "elasticsearch guide",
            "fields": ["title", "summary^3"]
        }
    },
    "_source": ["title", "summary", "publish_date"]
}
```

#### 3.4 Bool查询

- `must` 等同于 `AND`
- `must_not` 等同于 `NOT`
- `should` 等同于 `OR`

```shell
POST /bookdb_index/book/_search
{
    "query": {
        "bool": {
            "must": {
                "bool" : { "should": [
                      { "match": { "title": "Elasticsearch" }},
                      { "match": { "title": "Solr" }} ] }
            },
            "must": { "match": { "authors": "clinton gormely" }},
            "must_not": { "match": {"authors": "radu gheorge" }}
        }
    }
}
```

#### 3.5 模糊（Fuzzy）查询

```shell
POST /bookdb_index/book/_search
{
    "query": {
        "multi_match" : {
            "query" : "comprihensiv guide",
            "fields": ["title", "summary"],
            "fuzziness": "AUTO"
        }
    },
    "_source": ["title", "summary", "publish_date"],
    "size": 1
}
```

#### 3.6 通配符（Wildcard）查询

- `？` 匹配任何字符
- `*` 匹配零个或多个字符。

```
POST /bookdb_index/book/_search
{
    "query": {
        "wildcard" : {
            "authors" : "t*"
        }
    }
}
```

#### 3.7 正则（RegeXp）查询

```
POST /bookdb_index/book/_search
{
    "query": {
        "regexp" : {
            "authors" : "t[a-z]*y"
        }
    }
}
```

#### 3.8 短语匹配(Match Phrase)查询

**短语匹配查询** 要求在请求字符串中的所有查询项必须都在文档中存在，文中顺序也得和请求字符串一致，且彼此相连。默认情况下，查询项之间必须紧密相连，但可以设置 `slop` 值来指定查询项之间可以分隔多远的距离，结果仍将被当作一次成功的匹配。

```
POST /bookdb_index/book/_search
{
    "query": {
        "multi_match" : {
            "query": "search engine",
            "fields": ["title", "summary"],
            "type": "phrase",
            "slop": 3
        }
    }
}
```

#### 3.9 短语前缀（Match Phrase Prefix）查询

**短语前缀式查询** 能够进行 **即时搜索（search-as-you-type）** 类型的匹配，或者说提供一个查询时的初级自动补全功能，无需以任何方式准备你的数据。和 `match_phrase` 查询类似，它接收`slop` 参数（用来调整单词顺序和不太严格的相对位置）和 `max_expansions` 参数（用来限制查询项的数量，降低对资源需求的强度）

```shell
POST /bookdb_index/book/_search
{
    "query": {
        "match_phrase_prefix" : {
            "summary": {
                "query": "search en",
                "slop": 3,
                "max_expansions": 10
            }
        }
    },
    "_source": [ "title", "summary", "publish_date" ]
}
```

#### 3.10 查询字符串（Query String）

**查询字符串** 类型（**query_string**）的查询提供了一个方法，用简洁的简写语法来执行 **多匹配查询**、 **布尔查询** 、 **提权查询**、 **模糊查询**、 **通配符查询**、 **正则查询** 和**范围查询**。下面的例子中，我们在那些作者是 **“grant ingersoll”** 或 **“tom morton”** 的某本书当中，使用查询项 **“search algorithm”** 进行一次模糊查询，搜索全部字段，但给 `summary` 的权重提升 2 倍。

```
POST /bookdb_index/book/_search
{
    "query": {
        "query_string" : {
            "query": "(saerch~1 algorithm~1) AND (grant ingersoll)  OR (tom morton)",
            "fields": ["_all", "summary^2"]
        }
    }
}
```

#### 3.11 简单查询字符串（Simple Query String）

**简单请求字符串** 类型（**simple_query_string**）的查询是请求**字符串类型**（**query_string**）查询的一个版本，它更适合那种仅暴露给用户一个简单搜索框的场景；因为它用 `+/\|/-` 分别替换了 `AND/OR/NOT`，并且自动丢弃了请求中无效的部分，不会在用户出错时，抛出异常。

```
POST /bookdb_index/book/_search
{
    "query": {
        "simple_query_string" : {
            "query": "(saerch~1 algorithm~1) + (grant ingersoll)  | (tom morton)",
            "fields": ["_all", "summary^2"]
        }
    }
} 
```

#### 3.12 词条（Term）/多词条（Terms）查询

以上例子均为 `full-text`(全文检索) 的示例。有时我们对结构化查询更感兴趣，希望得到更准确的匹配并返回结果，**词条查询** 和 **多词条查询** 可帮我们实现。在下面的例子中，我们要在索引中找到所有由 **Manning** 出版的图书。

```
POST /bookdb_index/book/_search
{
    "query": {
        "term" : {
            "publisher": "manning"
        }
    }
}
```

#### 3.13  词条（Term）查询 - 排序（Sorted）

**词条查询** 的结果（和其他查询结果一样）可以被轻易排序，多级排序也被允许：

```
POST /bookdb_index/book/_search
{
    "query": {
        "term" : {
            "publisher": "manning"
        }
    },
    "_source" : ["title","publish_date","publisher"],
    "sort": [
        { "publish_date": {"order":"desc"}},
        { "title": { "order": "desc" }}
    ]
}
```

#### 3.14 范围查询

```
POST /bookdb_index/book/_search
{
    "query": {
        "range" : {
            "publish_date": {
                "gte": "2015-01-01",
                "lte": "2015-12-31"
            }
        }
    },
    "_source" : ["title","publish_date","publisher"]
}
```

#### 3.15 过滤(Filtered)查询

```
POST /bookdb_index/book/_search
{
    "query": {
        "filtered": {
            "query" : {
                "multi_match": {
                    "query": "elasticsearch",
                    "fields": ["title","summary"]
                }
            },
            "filter": {
                "range" : {
                    "num_reviews": {
                        "gte": 20
                    }
                }
            }
        }
    },
    "_source" : ["title","summary","publisher", "num_reviews"]
}
```

#### 3.16 多重过滤（Multiple Filters）

**多重过滤** 可以结合 **布尔查询** 使用

```
POST /bookdb_index/book/_search
{
    "query": {
        "filtered": {
            "query" : {
                "multi_match": {
                    "query": "elasticsearch",
                    "fields": ["title","summary"]
                }
            },
            "filter": {
                "bool": {
                    "must": {
                        "range" : { "num_reviews": { "gte": 20 } }
                    },
                    "must_not": {
                        "range" : { "publish_date": { "lte": "2014-12-31" } }
                    },
                    "should": {
                        "term": { "publisher": "oreilly" }
                    }
                }
            }
        }
    },
    "_source" : ["title","summary","publisher", "num_reviews", "publish_date"]
}
```

#### 3.17 作用分值: 域值（Field Value）因子

也许在某种情况下，你想把文档中的某个特定域作为计算相关性分值的一个因素，比较典型的场景是你想根据普及程度来提高一个文档的相关性。在我们的示例中，我们想把最受欢迎的书（基于评论数判断）的权重进行提高，可使用 `field_value_factor` 用以影响分值。

```
POST /bookdb_index/book/_search
{
    "query": {
        "function_score": {
            "query": {
                "multi_match" : {
                    "query" : "search engine",
                    "fields": ["title", "summary"]
                }
            },
            "field_value_factor": {
                "field" : "num_reviews",
                "modifier": "log1p",
                "factor" : 2
            }
        }
    },
    "_source": ["title", "summary", "publish_date", "num_reviews"]
}
```

#### 3.18作用分值: 衰变（Decay）函数

假设不想使用域值做递增提升，而你有一个理想目标值，并希望用这个加权因子来对这个离你较远的目标值进行衰减。有个典型的用途是基于经纬度、价格或日期等数值域的提升。在如下的例子中，我们查找在2014年6月左右出版的，查询项是 **search engines** 的书。

```
POST /bookdb_index/book/_search
{
    "query": {
        "function_score": {
            "query": {
                "multi_match" : {
                    "query" : "search engine",
                    "fields": ["title", "summary"]
                }
            },
            "functions": [
                {
                    "exp": {
                        "publish_date" : {
                            "origin": "2014-06-15",
                            "offset": "7d",
                            "scale" : "30d"
                        }
                    }
                }
            ],
            "boost_mode" : "replace"
        }
    },
    "_source": ["title", "summary", "publish_date", "num_reviews"]
}
```

#### 3.19 函数分值: 脚本评分

当内置的评分函数无法满足你的需求时，还可以用 **Groovy** 脚本。在我们的例子中，想要指定一个脚本，能在决定把 `num_reviews` 的因子计算多少之前，先将 `publish_date` 考虑在内。因为很新的书也许不会有评论，分值不应该被惩罚。

```
POST /bookdb_index/book/_search
{
    "query": {
        "function_score": {
            "query": {
                "multi_match" : {
                    "query" : "search engine",
                    "fields": ["title", "summary"]
                }
            },
            "functions": [
                {
                    "script_score": {
                        "params" : {
                            "threshold": "2015-07-30"
                        },
                        "script": "publish_date = doc['publish_date'].value; num_reviews = doc['num_reviews'].value; if (publish_date > Date.parse('yyyy-MM-dd', threshold).getTime()) { return log(2.5 + num_reviews) }; return log(1 + num_reviews);"
                    }
                }
            ]
        }
    },
    "_source": ["title", "summary", "publish_date", "num_reviews"]
}
```