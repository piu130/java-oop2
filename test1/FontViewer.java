package test.test1;

import javafx.application.Application;
import javafx.beans.binding.Bindings;
import javafx.beans.property.*;
import javafx.collections.FXCollections;
import javafx.geometry.Orientation;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCharacterCombination;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyCodeCombination;
import javafx.scene.input.KeyCombination;
import javafx.scene.layout.*;
import javafx.scene.text.*;
import javafx.stage.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * This frame contains a text sample, a control panel, and
 * a menu structure to change the font of the text.
 */
public class FontViewer extends Application {

    private static final double SMALL_SIZE = 24.0;
    private static final double MEDIUM_SIZE = 36.0;
    private static final double LARGE_SIZE = 48.0;

    /**
     * Font sizes
     */
    private static final Map<String, Double> sizes = new HashMap<String, Double>() {{
        put("Small", SMALL_SIZE);
        put("Medium", MEDIUM_SIZE);
        put("Large", LARGE_SIZE);
    }};

    /**
     * Font families
     */
    private static final ArrayList<String> fontFamilies = new ArrayList<String>() {{
        add("Serif");
        add("SansSerif");
        add("Monospaced");
    }};

    /**
     * Font properties
     */
    private StringProperty fontFamilyProperty = new SimpleStringProperty("Arial");
    private BooleanProperty fontWeightProperty = new SimpleBooleanProperty(true);
    private BooleanProperty fontPostureProperty = new SimpleBooleanProperty(false);
    private DoubleProperty fontSizeProperty = new SimpleDoubleProperty(36.0);

    @Override
    public void start(final Stage stage) {
        BorderPane pane = new BorderPane();
        pane.setCenter(createLabel());
        pane.setBottom(createControlBox());
        pane.setTop(createMenuBar());
        createBindings();

        Scene scene = new Scene(pane);
        stage.setScene(scene);
        stage.setTitle("Font Viewer");
        stage.setWidth(500);
        stage.show();
    }

    /**
     * Creates the label with font bind to the font properties
     * @return Label with image
     */
    private Node createLabel() {
        Label label = new Label("Big Java");

        label.fontProperty().bind(Bindings.createObjectBinding(
            this::updateLabelFont,
            fontFamilyProperty,
            fontWeightProperty,
            fontPostureProperty,
            fontSizeProperty
        ));

        return new HBox(label, new ImageView(getClass().getResource("BigJava.jpg").toExternalForm()));
    }

    /**
     * Creates the control box
     * @return Box to control font
     */
    private Node createControlBox() {
        VBox vBox = new VBox(
            createFontSizeLabel(),
            createFaceNameCombo(),
            createStyleCheckBoxes(),
            createSizeButtons()
        );
        vBox.setSpacing(5.0);
        vBox.setAlignment(Pos.CENTER);

        return new HBox(
            createSizeSlider(),
            vBox
        );
    }

    /**
     * Creates the label which displays the size
     * @return Label with font size binding
     */
    private Label createFontSizeLabel() {
        Label fontSizeLabel = new Label();
        fontSizeLabel.textProperty().bind(Bindings.concat("Font size: ", Bindings.format("%3.0f", fontSizeProperty)));

        return fontSizeLabel;
    }

    /**
     * Creates the face name control element
     * @return Combo box with font families
     */
    private ComboBox<String> createFaceNameCombo() {
        ComboBox<String> faceNameCombo = new ComboBox<>(FXCollections.observableArrayList(fontFamilies));
        faceNameCombo.setEditable(true);
        faceNameCombo.setValue(fontFamilyProperty.getValue());

        fontFamilyProperty.bindBidirectional(faceNameCombo.valueProperty());

        return faceNameCombo;
    }

    /**
     * Creates the checkboxes italic and bold
     * @return Checkbox italic and bold
     */
    private Node createStyleCheckBoxes() {
        CheckBox italicCheckBox = new CheckBox("Italic");
        italicCheckBox.setSelected(fontPostureProperty.getValue());
        fontPostureProperty.bindBidirectional(italicCheckBox.selectedProperty());

        CheckBox boldCheckBox = new CheckBox("Bold");
        boldCheckBox.setSelected(fontWeightProperty.getValue());
        fontWeightProperty.bindBidirectional(boldCheckBox.selectedProperty());

        HBox hBox = new HBox(italicCheckBox, boldCheckBox);
        hBox.setAlignment(Pos.CENTER);

        return hBox;
    }

    /**
     * Creates the size radio buttons
     * @return Radio buttons defined in this.sizes
     */
    private Node createSizeButtons() {
        ToggleGroup sizeButtonGroup = new ToggleGroup();
        HBox sizeBox = new HBox();
        sizes.forEach((text, size) -> {
            RadioButton radioButton = new RadioButton(text);
            radioButton.setUserData(size);
            radioButton.setToggleGroup(sizeButtonGroup);
            radioButton.setSelected(fontSizeProperty.getValue().equals(size));
            sizeBox.getChildren().add(radioButton);
        });

        sizeButtonGroup.getToggles().forEach(toggle -> {
            RadioButton button = (RadioButton) toggle;
            button.setOnAction(event -> fontSizeProperty.setValue((double) toggle.getUserData()));
        });

        fontSizeProperty.addListener((observable, oldValue, newValue) -> {
            sizeButtonGroup.getToggles().forEach(toggle -> {
                Double size = (double) toggle.getUserData();
                toggle.setSelected(Math.round(newValue.floatValue()) == size);
            });

            updateLabelFont();
        });

        return sizeBox;
    }

    /**
     * Creates the size slider
     * @return Slider to change font size
     */
    private Slider createSizeSlider() {
        Slider slider = new Slider(10, 70, fontSizeProperty.getValue());
        slider.setMajorTickUnit(10);
        slider.setShowTickMarks(true);
        slider.setShowTickLabels(true);
        slider.setOrientation(Orientation.VERTICAL);

        fontSizeProperty.bindBidirectional(slider.valueProperty());

        return slider;
    }

    /**
     * Creates the menu bar
     * @return Menu bar
     */
    private MenuBar createMenuBar() {
        Menu faceMenu = new Menu("Face");
        fontFamilies.forEach(fontFamily -> {
            MenuItem item = new MenuItem(fontFamily);
            faceMenu.getItems().add(item);
        });

        faceMenu.setOnAction(event -> {
            MenuItem item = (MenuItem) event.getTarget();
            fontFamilyProperty.setValue(item.getText());
        });

        CheckMenuItem italicCheckMenuItem = new CheckMenuItem("Italic");
        italicCheckMenuItem.setSelected(fontPostureProperty.getValue());
        italicCheckMenuItem.setAccelerator(new KeyCodeCombination(KeyCode.I, KeyCombination.CONTROL_DOWN));
        fontPostureProperty.bindBidirectional(italicCheckMenuItem.selectedProperty());

        CheckMenuItem boldCheckMenuItem = new CheckMenuItem("Bold");
        boldCheckMenuItem.setSelected(fontWeightProperty.getValue());
        boldCheckMenuItem.setAccelerator(new KeyCodeCombination(KeyCode.B, KeyCombination.CONTROL_DOWN));
        fontWeightProperty.bindBidirectional(boldCheckMenuItem.selectedProperty());

        Menu style = new Menu("Style", null, italicCheckMenuItem, boldCheckMenuItem);
        Menu menu = new Menu("Font", null, faceMenu, new SeparatorMenuItem(), style);

        return new MenuBar(menu);
    }

    /**
     * Create bindings that doesn't belong to a control element
     */
    private void createBindings() {
        fontFamilyProperty.addListener((observable, oldValue, newValue) -> updateLabelFont());
    }

    /**
     * Updates the font according to the properties
     * @return Font calculated by properties
     */
    private Font updateLabelFont() {
        return Font.font(
            fontFamilyProperty.getValue(),
            fontWeightProperty.getValue() ? FontWeight.BOLD : FontWeight.NORMAL,
            fontPostureProperty.getValue() ? FontPosture.ITALIC : FontPosture.REGULAR,
            fontSizeProperty.doubleValue()
        );
    }

    public static void main(String[] args) {
        Application.launch(args);
    }
}
