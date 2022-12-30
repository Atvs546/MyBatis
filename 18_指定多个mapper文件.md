1.在mappers标签中添加mapper标签，并指定mapper文件的全限定民称(mapper文件过多时很繁琐)

2.在mappers标签中添加<package  name="">name属性指mapper文件所在的包名，有多个包可以再加，这个包下的所有的mapper文件都可以加载给mybatis

​            要求：1.mapper文件命名和dao接口的名称一定要保持一致

​                       2.mapper文件和dao接口在同一个目录下          