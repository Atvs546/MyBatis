以下是jdbc连接数据库的操作存在的缺陷



1.代码比较多
2.需要关注Connection，Statement,ResultSet对象的创建和销毁
3.对ResultSet查询的结果,需要自己封装为List
4.重复的代码较多
5.业务代码和数据库操作混在一起



```java
  Connection conn = null;
      PreparedStatement ps=null ;
      ResultSet rs=null;
      String url="jdbc:mysql://localhost:3306/liu";
      String user="root";
      String password="123456";
      try {
          //连接数据库
          conn= DriverManager.getConnection(url, user, password);
          //3、获取预编译的数据库操作对象,?是占位符
          String sql="select * from user where ename=? ";
          ps=conn.prepareStatement(sql);
          //给占位符？传值
          ps.setString(1,"liuyong");
          rs=ps.executeQuery();
          while (rs.next()){
              int a=rs.getInt("biaoshi");
              String b=rs.getString("ename");
              String c=rs.getString("epassword");
              System.out.println(a+"\t"+"\t"+"\t"+b+"\t"+"\t"+"\t"+c);
          }
      
      } catch (SQLException e) {
          e.printStackTrace();
      } finally {
          //6、释放资源
          if (ps != null) {
              try {
                  ps.close();
              } catch (SQLException e) {
                  e.printStackTrace();
              }
          }
          if (conn != null) {
              try {
                  conn.close();
              } catch (SQLException e) {
                  e.printStackTrace();
              }
          }
      }
                }
            }
       
```