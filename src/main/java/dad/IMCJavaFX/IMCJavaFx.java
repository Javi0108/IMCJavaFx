package dad.IMCJavaFX;

import javafx.application.Application;
import javafx.beans.binding.Bindings;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.util.converter.NumberStringConverter;

public class IMCJavaFx extends Application{
		
		private StringProperty pesoStringP = new SimpleStringProperty(this, "peso");
		private DoubleProperty pesoDoubleP = new SimpleDoubleProperty(this, "pesoValor");
		
		private StringProperty alturaStringP = new SimpleStringProperty(this, "altura");
		private DoubleProperty alturaDoubleP = new SimpleDoubleProperty(this, "alturaValor");
		
		private StringProperty IMCStringP = new SimpleStringProperty(this, "imc");
		private DoubleProperty IMCDoubleP = new SimpleDoubleProperty(this, "imcValor");
		
		private StringProperty resulStringP = new SimpleStringProperty(this, "resultado");
	
		public final double BAJO_PESO = 18.5;
		public final double NORMAL = 25;
		public final double SOBREPESO = 30;
		
		private TextField pesoText, alturaText;
		private Label pesoLabel, kgLabel, alturaLabel, cmLabel;
		
		public void calcularIMC() {
			Double IMC = IMCDoubleP.get();
			
			if (IMC == 0d) {
				IMCStringP.set("IMC: (peso * altura^ 2)");
				resulStringP.set("Bajo peso | Normal | Sobrepeso | Obeso");
			} else {
				IMCStringP.set("IMC: " + IMC);
				if (IMC < BAJO_PESO)
					resulStringP.set("Bajo peso");
				else if (IMC >= BAJO_PESO && IMC < NORMAL)
					resulStringP.set("Normal");
				else if (IMC >= NORMAL && IMC < SOBREPESO)
					resulStringP.set("Sobrepeso");
				else
					resulStringP.set("Obeso");
			}
		}
		
		private void recalcularIMC() {
			if ((pesoDoubleP.get() == 0d) || (alturaDoubleP.get() == 0d))
				IMCDoubleP.set(0d);
			else {
				Double p = pesoDoubleP.get();
				Double a = alturaDoubleP.get();
				Double result = (p / (a * a)) * 10000d;
				IMCDoubleP.set(Math.round(result * 100.0) / 100.0);
			}
		}	
		
		@Override
		public void start(Stage primaryStage) throws Exception {
			
			pesoLabel = new Label("Peso:");
			pesoText = new TextField();
			pesoText.setPrefColumnCount(4);
			kgLabel = new Label("kg");
			
				HBox pesoBox = new HBox();
				pesoBox.setAlignment(Pos.BASELINE_CENTER);
				pesoBox.setSpacing(5);
				pesoBox.getChildren().addAll(pesoLabel, pesoText, kgLabel);
			
			alturaLabel = new Label("Altura:");
			alturaText = new TextField();
			alturaText.setPrefColumnCount(4);
			cmLabel = new Label("cm");
			
				HBox alturaBox = new HBox();
				alturaBox.setAlignment(Pos.BASELINE_CENTER);
				alturaBox.setSpacing(5);
				alturaBox.getChildren().addAll(alturaLabel, alturaText, cmLabel);
			
			Label IMC = new Label("IMC: (peso * altura^ 2)");
			Label resulPeso = new Label("Bajo peso | Normal | Sobrepeso | Obeso");
			
				VBox root = new VBox();
				root.setSpacing(5);
				root.setAlignment(Pos.CENTER);
				root.getChildren().addAll(pesoBox, alturaBox, IMC, resulPeso);		
			
			Scene scene = new Scene(root, 320, 200);
			
			primaryStage.setTitle("Índice de Masa Corporal");
			primaryStage.setScene(scene);
			primaryStage.show();
			
			pesoStringP.bindBidirectional(pesoText.textProperty());
			Bindings.bindBidirectional(pesoStringP, pesoDoubleP, new NumberStringConverter());
			pesoDoubleP.addListener((o, ov, nv) -> recalcularIMC());
			
			alturaStringP.bindBidirectional(alturaText.textProperty());
			Bindings.bindBidirectional(alturaStringP, alturaDoubleP, new NumberStringConverter());
			alturaDoubleP.addListener((o, ov, nv) -> recalcularIMC());
			
			IMCStringP.bindBidirectional(IMC.textProperty());
			resulStringP.bindBidirectional(resulPeso.textProperty());
			IMCDoubleP.addListener((o, ov, nv) -> calcularIMC());

		}			
		
		public static void main(String[] args) {
			launch(args);

		}

}
