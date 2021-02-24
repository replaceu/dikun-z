package com.dikun.goodsService;


import com.baomidou.mybatisplus.annotation.DbType;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.generator.AutoGenerator;
import com.baomidou.mybatisplus.generator.config.DataSourceConfig;
import com.baomidou.mybatisplus.generator.config.GlobalConfig;
import com.baomidou.mybatisplus.generator.config.PackageConfig;
import com.baomidou.mybatisplus.generator.config.StrategyConfig;
import com.baomidou.mybatisplus.generator.config.rules.DateType;
import com.baomidou.mybatisplus.generator.config.rules.NamingStrategy;
import org.junit.jupiter.api.Test;

public class CodeGenerator {
    @Test
    public void run(){

        //1.创建代码生成器
        AutoGenerator mpg = new AutoGenerator();

        //2.全局配置
        GlobalConfig gc = new GlobalConfig();
        String projectPath = System.getProperty("goods.fir");
        System.out.println(projectPath);

        gc.setOutputDir("D:\\workspace_idea\\dikun_parent\\service\\service_stock" + "/src/main/java");
        gc.setAuthor("dikun");
        gc.setOpen(false);//生成后是否打开资源管理器
        gc.setFileOverride(false);//重新生成文件时是否覆盖
        /*
         * mp生成service层代码，默认接口名称第一个字母有 I
         * UcenterService
         * */
        gc.setServiceName("%sService");//去掉Service接口首字母I
        gc.setIdType(IdType.ID_WORKER);//主键策略
        gc.setDateType(DateType.ONLY_DATE);//定义生成的实体类中日期类型
        gc.setSwagger2(true);//开启Swagger2模式

        mpg.setGlobalConfig(gc);//将全局配置传入到代码生产器


        //3.数据源配置
        DataSourceConfig dsc = new DataSourceConfig();
        dsc.setUrl("jdbc:mysql://localhost:3306/sale?serverTimezone=GMT%2B8");
        dsc.setDriverName("com.mysql.cj.jdbc.Driver");
        dsc.setUsername("root");
        dsc.setPassword("19940818zj");
        dsc.setDbType(DbType.MYSQL);

        mpg.setDataSource(dsc);

        //4.包配置
        PackageConfig pc = new PackageConfig();
        pc.setModuleName("servicestock"); //模块名
        pc.setParent("com.dikun");
        pc.setController("controller");
        pc.setEntity("entity");
        pc.setService("service");
        pc.setMapper("mapper");
        mpg.setPackageInfo(pc);

        //5.策略配置
        StrategyConfig sc = new StrategyConfig();
        sc.setInclude("goods");//表名称
        sc.setNaming(NamingStrategy.underline_to_camel);//数据库表映射到实体的命名策略
        sc.setTablePrefix(pc.getModuleName()+"_");//生成实体时去掉表前缀
        sc.setColumnNaming(NamingStrategy.underline_to_camel);////数据库表字段映射到实体的命名策略
        sc.setEntityLombokModel(true);// lombok 模型 @Accessors(chain = true) setter链式操作
        sc.setRestControllerStyle(true);//restful api风格控制器
        sc.setControllerMappingHyphenStyle(true);//url中驼峰转连字符

        mpg.setStrategy(sc);

        mpg.execute();

    }

}
