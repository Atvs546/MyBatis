1.接口中定义方法

Map<Object,Object> selectMapById( Integer id);



2.在mapper文件中指定sql的标签的resultType="java.util.HashMap"更改查询后转化的目标

<select *id*="selectbyid"  *resultType*="java.util.Map">
    select *from people where id=#{id}
</select>

 

3.执行完sql语句用Map集合来接收



返回map集合只能是返回一行数据，多一行都会出错

# 