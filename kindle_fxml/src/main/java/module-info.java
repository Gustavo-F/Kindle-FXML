module br.com.gustavoferreira.kindle_fxml {
    requires javafx.controls;
    requires javafx.fxml;
	requires java.sql;
	requires java.base;

    opens br.com.gustavoferreira.kindle_fxml to javafx.fxml;
    exports br.com.gustavoferreira.kindle_fxml;
}