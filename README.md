[![Build Status](https://travis-ci.org/eugener/oxbow.svg?branch=master)](https://travis-ci.org/eugener/oxbow)


**SwingBits** is a collection of useful components and utilities for Java Swing Toolkit. 

The documentation can be found in the [Project Wiki](https://github.com/eugener/oxbow/wiki/_pages).   

```
 NOTE: The TaskDialog library is becoming a part of SwingBits starting with SwingBits v1.0 
```

Here are some of its features:
   
#### Table Filtering

![Table Filtering](https://github.com/eugener/oxbow/wiki/images/TableFiltering-seach-actions.png)

#### Task Dialogs    

![Nimbus LAF Task Dialog](https://github.com/eugener/oxbow/wiki/images/TaskDialog-error-metal.png)

![Windows LAF Task Dialog](https://github.com/eugener/oxbow/wiki/images/TaskDialog-error-win.png)

![Task Dialog 3](https://github.com/eugener/oxbow/wiki/images/TaskDialog-error-mac.png)

![Task Dialog 5](https://github.com/eugener/oxbow/wiki/images/TaskDialog-commandLinks-win.png)

![Windows LAF Exception Dialog](https://github.com/eugener/oxbow/wiki/images/TaskDialog-exception-win.jpg)

#### Example : Table Filtering

The maven pom file:
```maven
<project xmlns="http://maven.apache.org/POM/4.0.0"
	xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
	xsi:schemaLocation="http://maven.apache.org/POM/4.0.0 https://maven.apache.org/xsd/maven-4.0.0.xsd">
	<modelVersion>4.0.0</modelVersion>
	<groupId>TestJTable</groupId>
	<artifactId>TestJTable</artifactId>
	<version>0.0.1-SNAPSHOT</version>
	<build>
		<sourceDirectory>src</sourceDirectory>
		<plugins>
			<plugin>
				<artifactId>maven-compiler-plugin</artifactId>
				<version>3.8.0</version>
				<configuration>
					<source>1.8</source>
					<target>1.8</target>
				</configuration>
			</plugin>
		</plugins>
	</build>
	<dependencies>
		<dependency>
			<groupId>org.swinglabs.swingx</groupId>
			<artifactId>swingx-all</artifactId>
			<version>1.6.5-1</version>
		</dependency>
		<dependency>
			<groupId>org.bidib.org.oxbow</groupId>
			<artifactId>swingbits</artifactId>
			<version>1.2.2</version>
		</dependency>
	</dependencies>
</project>
```

JAVA code sample :
```java
import java.awt.BorderLayout;

import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.SwingUtilities;

import org.jdesktop.swingx.JXTable;
import org.jdesktop.swingx.decorator.HighlighterFactory;
import org.oxbow.swingbits.list.CheckListRenderer;
import org.oxbow.swingbits.table.filter.TableRowFilterSupport;
 
 public class FilterTable extends JFrame{

	private static final long serialVersionUID = 1L;

	public FilterTable() {
		 setLayout(new BorderLayout());
         setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
         Object rows[][] = {
           {"AMZN", "Amazon", 41.28},
           {"EBAY", "eBay", 41.57},
           {"GOOG", "Google", 388.33},
           {"MSFT", "Microsoft", 26.56},
           {"NOK", "Nokia Corp", 17.13},
           {"ORCL", "Oracle Corp.", 12.52},
           {"SUNW", "Sun Microsystems", 3.86},
           {"TWX",  "Time Warner", 17.66},
           {"VOD",  "Vodafone Group", 26.02},
           {"YHOO", "Yahoo!", 37.69}
         };
		Object columns[] = { "Symbol", "Name", "Price" };

		JXTable table = new JXTable(rows, columns);
		table.setColumnControlVisible(true);
		table.setHighlighters(HighlighterFactory.createSimpleStriping());
		table.setEditable(false);
		TableRowFilterSupport.forTable(table).actions(true).searchable(true).checkListRenderer(new CheckListRenderer()).apply();
		add(new JScrollPane(table));
		setSize(300, 250);
		setVisible(true);
	}

	public static void main(String args[]) {
		SwingUtilities.invokeLater(() -> new FilterTable());
	}
 }
 ```
 
