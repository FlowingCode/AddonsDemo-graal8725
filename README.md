Reproducer for https://github.com/oracle/graal/issues/8725

1. Compile as a native image `mvn clean -Pproduction -Pnative native:compile -DskipTests`

2. Start the application `target/native-image`
3. Open http://127.0.0.1:8080/ in your browser
4. Take note of the following lines from the log

![image](https://github.com/FlowingCode/AddonsDemo/assets/11554739/8f7ca703-6c74-4171-a7b8-9b59389bdee1)


(If it says "Metadata count: 27" with no "No package metadata" message, recompile)

5. Stop the application and restart the native image. Observe that the same lines are logged.

6. Stop the application (Ctrl^C) and start this time as a Java application (java -jar target/addons-demos-24.jar)

7. Verify that the "Metadata count" is higher, and the hamburger menu includes entries for the items that had "no package metadata" in the native image.
```
2024-04-17T20:29:51.281-03:00  WARN 11516 --- [nio-8080-exec-3] c.flowingcode.vaadin.addons.DemoLayout   : Metadata count: 27
```
![image](https://github.com/FlowingCode/AddonsDemo/assets/11554739/2b6aeef5-0934-44be-bcc7-8b36c5e5d720)


8. Repeat steps 1-4. Observe that a different set of classes has "no package metadata"


The first time the page is loaded, it executes this code 
https://github.com/FlowingCode/AddonsDemo-graal8725/blob/main/src/main/java/com/flowingcode/vaadin/addons/DemoLayout.java#L46-L55

Dependencies are from https://maven.flowingcode.com/snapshots. Each dependency is actually as a pair of artifacts with a split package: the `demo` dependency shares a split-package with the non-demo dependency (both have the same descriptors in MANIFEST.MF), and that package is the one reflected at runtime. The class upon which `clazz.getPackage()` is called is always from the `demo` dependency, and the split-package is not used by any other dependency. For instance `com.flowingcode.vaadin.addons.badgelist` is available from these dependencies.
```
        <dependency>
            <groupId>com.flowingcode.vaadin.addons</groupId>
            <artifactId>badge-list-addon</artifactId>
            <version>${badge-list.version}</version>
        </dependency>
        
        <dependency>
            <groupId>com.flowingcode.vaadin.addons</groupId>
            <artifactId>badge-list-addon</artifactId>
            <version>${badge-list.version}</version>
            <classifier>demo</classifier>
        </dependency>
```

