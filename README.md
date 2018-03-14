# vagary
数据库访问中间件，统一的标准sql查询，底层可以是不同的数据库包括mysql、ElasticSearch、kylin、presto等。

统一的访问请求参数，通过buildsql将请求参数转化为标准sql，当前只写了对mysql的标准实现，其他数据源还有待提升。

