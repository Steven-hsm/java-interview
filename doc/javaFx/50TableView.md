JavaFX TableView使您能够在JavaFX应用程序中显示表视图。JavaFX TableView由JavaFX .scene.control.TableView类表示

### 1. 和TableView关联的类

- TableColumn
- TableRow
- TableCell
- TablePosition
- TableViewFocusModel
- TableViewSelectionModel

### 2. 创建TableView

```java
TableView tableView = new TableView();
```

**添加TableColumn到TableView**

```java
TableView tableView = new TableView();

TableColumn<Person, String> firstNameColumn = TableColumn<>("First Name");
firstNameColumn.setCellValueFactory(new PropertyValueFactory<>("firstName"));

TableColumn<Person, String> lastNameColumn = new TableColumn<>("Last Name");
lastNameColumn.setCellValueFactory(new PropertyValueFactory<>("lastName"));
```

tableccolumn必须在其上设置单元格值工厂。单元格值工厂提取要在列的每个单元格(每行)中显示的值。在上面的示例中使用了PropertyValueFactory。PropertyValueFactory工厂可以从Java对象中提取属性值(字段值)。属性的名称作为参数传递给PropertyValueFactory构造函数，如下所示:

```java
PropertyValueFactory factory = new PropertyValueFactory<>("firstName");
```

属性名firstName将与Person对象的getter getter方法getFirstName()匹配，后者包含每行显示的值。

在前面的示例中，第二个PropertyValueFactory设置在第二个TableColumn实例上。传递给第二个PropertyValueFactory的属性名是lastName，它将匹配Person类的getter方法getLastName()。

### 3. 添加数据到TableView

```java
tableView.getItems().add(new Person("John", "Doe"));
tableView.getItems().add(new Person("Jane", "Deer"))
```

### 4. 没有数据展示时使用占位符

```java
tableView.setPlaceholder(new Label("No rows to display"));
```

### 5.TableView选中节点

**获取选中节点实例**

```java
TableViewSelectionModel selectionModel = tableView.getSelectionModel();
```

**设置选中模式**

```java
// set selection mode to only 1 row
selectionModel.setSelectionMode(SelectionMode.SINGLE);

// set selection mode to multiple rows
selectionModel.setSelectionMode(SelectionMode.MULTIPLE);
```

**获取选中的行**

```java
ObservableList selectedItems = selectionModel.getSelectedItems();
```

```java
ObservableList<Person> selectedItems = selectionModel.getSelectedItems();
```

**获取选中行的索引**

```java
ObservableList<Integer> selectedIndices = selectionModel.getSelectedIndices();
```

**清楚所选择的行**

```java
selectionModel.clearSelection();
```

**监听选择行改变**

```java
ObservableList<Person> selectedItems = selectionModel.getSelectedItems();

selectedItems.addListener(new ListChangeListener<Person>() {
  @Override
  public void onChanged(Change<? extends Person> change) {
    System.out.println("Selection changed: " + change.getList());
  }
})
```

**代码选中行**

```java
selectionModel.select(1);
```

### 6. 内嵌节点

```java
TableView tableView = new TableView();
    
TableColumn<Customer, String> customer   = new TableColumn<>("Customer");
TableColumn<Customer, String> customerId = new TableColumn<>("Customer No");
TableColumn<Customer, String> name       = new TableColumn<>("Name");
TableColumn<Customer, String> firstName  = new TableColumn<>("First Name");
TableColumn<Customer, String> lastName   = new TableColumn<>("Last Name");

name     .getColumns().addAll(firstName, lastName);
customer .getColumns().addAll(customerId, name);
tableView.getColumns().addAll(customer);

customerId.setCellValueFactory(new PropertyValueFactory<>("customerNo"));
firstName .setCellValueFactory(new PropertyValueFactory<>("firstName"));
lastName  .setCellValueFactory(new PropertyValueFactory<>("lastName"));

tableView.getItems().add(new Customer("007", "Jane", "Deer"));
tableView.getItems().add(new Customer("006", "John", "Doe"));
tableView.getItems().add(new Customer("008", "Mack", "Alamo"));
```

### 7. 获取显示的列

```java
TableColumn<String, Customer> leafColumn =
    tableView.getVisibleLeafColumn(1);
```

### 8.隐藏显示列

```java
tableColumn.setVisible(false);
tableColumn.setVisible(true);
```

### 9.对列进行重排序

1. 手动排

2. 代码排(将第一行移动到末尾)

   ```java
   tableView.getColumns().add(
       tableView.getColumns().remove(0));
   ```

### 10.行排序

```java
tableColumn.setSortType(TableColumn.SortType.ASCENDING);
tableColumn.setSortType(TableColumn.SortType.DESCENDING);
```

**禁止排序**

```
tableColumn.setSortable(false);
```

**自定义排序**

```java
Comparator<String> columnComparator =
    (String v1, String v2) -> {

  return v1.toLowerCase().compareTo(
        v2.toLowerCase());

};

tableColumn.setComparator(columnComparator);
```

**默认排序**

tableccolumn上有一个默认的Comparator设置，它可以为您执行某种智能形式的默认排序。有时候，默认的Comparator就足够了。下面是默认Comparator比较同一列中显示的两个值的方法:

检查空值。空值被认为小于非空值。如果两个值都为空，则认为它们相等。

如果第一个值是一个实现Comparable接口的对象，那么默认Comparator将返回value1.compareTo(value2)。

如果规则1和规则2没有应用到这些值-默认的Comparator使用它们的. tostring()方法将这两个值转换为string，并对它们进行比较。

**排序节点**

TableColumn类使用一个“排序节点”来显示当前应用于该列的排序类型。排序节点显示在列标题中。默认排序Node是一个类似箭头的三角形

您可以通过setSortNode()方法更改tableccolumn的排序节点，传递一个JavaFX Node作为排序节点。下面是一个设置ImageView作为表列排序节点的例子:

```java
FileInputStream input = new FileInputStream("images/sort-image.png");
Image image = new Image(input);
ImageView sortImage = new ImageView(image);

tableColumn.setSortNode(sortImage);
```

**自定义排序顺序**

```java
 tableView.getSortOrder().addAll(lastNameColumn, firstNameColumn);
```

**手动出发排序**

```java
tableView.sort();
```

**排序事件**

```java
tableView.setOnSort((event) -> {
    System.out.println("Sorting items");
});
```

```java
tableView.setOnSort((event) -> {
    event.consume();
});
```

### 11.行的虚拟化渲染

JavaFX TableView中的单元格渲染是虚拟化的。这意味着，TableView只会为可见的行/列创建单元格渲染对象，而不是为备份数据集中的所有行。因此，如果一个TableView包含1000行，但一次只有10行可见，那么TableView将只为10行可见的行创建单元格渲染对象，而不是为所有1000行。当用户在TableView中显示的数据集中滚动数据时，已经创建的单元格呈现对象将被重用，以显示可见的行。这样，TableView可以处理非常大的数据集，而单元格渲染对象的内存开销更少。

**自定义渲染**

```java
customerColumn.setCellFactory((tableColumn) -> {
    TableCell<Customer, String> tableCell = new TableCell<>() {
        @Override
        protected void updateItem(String item, boolean empty) {
            super.updateItem(item, empty);

            this.setText(null);
            this.setGraphic(null);

            if(!empty){
                this.setText(item.toUpperCase());
            }
        }
    };

    return tableCell;
});
```

### 12. 可编辑

可以使JavaFX TableView可编辑。使JavaFX TableView可编辑需要几个步骤。首先，您必须调用TableView的setEditable()方法，并传递一个true值作为参数。下面是它的样子:

```java
tableView.setEditable(true);
```

其次，您必须在您想要编辑的tableccolumn上设置一个不同的单元格呈现器。JavaFX自带4个内置的单元渲染器，你可以使用:

- TextFieldTableCell
- CheckBoxTableCell
- ChoiceBoxTableCell
- ComboBoxTableCell

```java
TableView tableView = new TableView();
tableView.setEditable(true);

TableColumn<Person, String> column1 = new TableColumn<>("First Name");
column1.setCellValueFactory(new PropertyValueFactory<>("firstName"));

TableColumn<Person, String> column2 = new TableColumn<>("Last Name");
column2.setCellValueFactory(new PropertyValueFactory<>("lastName"));
column2.setCellFactory(TextFieldTableCell.<Person>forTableColumn());

TableColumn<Person, String> column3 = new TableColumn<>("Category");
column3.setCellValueFactory(new PropertyValueFactory<>("category"));
column3.setCellFactory(ComboBoxTableCell.<Person, String>forTableColumn("Category 1", "Category 2"));

TableColumn<Person, Boolean> column4 = new TableColumn<>("Is XYZ");
column4.setCellValueFactory( cellData -> new ReadOnlyBooleanWrapper(cellData.getValue().getIsXyz()));
column4.setCellFactory(CheckBoxTableCell.<Person>forTableColumn(column4));


tableView.getColumns().add(column1);
tableView.getColumns().add(column2);
tableView.getColumns().add(column3);
tableView.getColumns().add(column4);

tableView.getItems().add(new Person("John"  , "Doe"  , null, false));
tableView.getItems().add(new Person("Jane"  , "Deer" , "Category 1", true));
tableView.getItems().add(new Person("Dinesh", "Gupta", "Category 2", true));
```

### 13.完整案例

```java
import javafx.application.Application;
import javafx.beans.property.ReadOnlyBooleanWrapper;
import javafx.scene.Scene;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.CheckBoxTableCell;
import javafx.scene.control.cell.ComboBoxTableCell;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
public class TableViewExample extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {

        TableView tableView = new TableView();
        tableView.setEditable(true);

        TableColumn<Person, String> column1 = new TableColumn<>("First Name");
        column1.setCellValueFactory(new PropertyValueFactory<>("firstName"));

        TableColumn<Person, String> column2 = new TableColumn<>("Last Name");
        column2.setCellValueFactory(new PropertyValueFactory<>("lastName"));
        column2.setCellFactory(TextFieldTableCell.<Person>forTableColumn());

        TableColumn<Person, String> column3 = new TableColumn<>("Category");
        column3.setCellValueFactory(new PropertyValueFactory<>("category"));
        column3.setCellFactory(ComboBoxTableCell.<Person, String>forTableColumn("Category 1", "Category 2"));

        TableColumn<Person, Boolean> column4 = new TableColumn<>("Is XYZ");
        column4.setCellValueFactory( cellData -> new ReadOnlyBooleanWrapper(cellData.getValue().isXyz()));
        column4.setCellFactory(CheckBoxTableCell.<Person>forTableColumn(column4));


        tableView.getColumns().add(column1);
        tableView.getColumns().add(column2);
        tableView.getColumns().add(column3);
        tableView.getColumns().add(column4);

        tableView.getItems().add(new Person("John"  , "Doe"  , null, false));
        tableView.getItems().add(new Person("Jane"  , "Deer" , "Category 1", true));
        tableView.getItems().add(new Person("Dinesh", "Gupta", "Category 2", true));

        VBox vbox = new VBox(tableView);

        Scene scene = new Scene(vbox);

        primaryStage.setScene(scene);

        primaryStage.show();
    }
}
```

```java
@Data
public class Person {


    private String firstName = null;
    private String lastName = null;

    private String category = null;

    private boolean isXyz = true;

    public Person() {
    }

    public Person(String firstName, String lastName) {
        this.firstName = firstName;
        this.lastName = lastName;
    }

    public Person(String firstName, String lastName, String category, boolean isXyz) {
        this.firstName = firstName;
        this.lastName  = lastName;
        this.category  = category;
        this.isXyz     = isXyz;
    }

}
```