package application.control;

import application.model.Log;
import application.model.Logger2;
import javafx.beans.value.ObservableValue;
import javafx.beans.value.ObservableValueBase;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableColumn.CellDataFeatures;
import javafx.scene.control.TableView;
import javafx.util.Callback;

public class LoggerReportController2 extends GeneralController{
	@FXML TableColumn<String, String> TagColumn;
	@FXML TableView<String> LogTable;
	
	@FXML
	private void initialize() {
		Logger2 peerMentorLogger = getLogger();
		
		ObservableList<String> activities = FXCollections.observableList(peerMentorLogger.getValidActivities());
		
		
		TagColumn.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<String,String>, ObservableValue<String>>() {
			
			@Override
			public ObservableValue<String> call(CellDataFeatures<String, String> param) {
				
				return new ObservableValueBase<String>() {

					@Override
					public String getValue() {
						return param.getValue();
					}
				};
			}
		});
		
		LogTable.setItems(activities);
		System.out.println(TagColumn.getPrefWidth());
		
		peerMentorLogger.entrySet().forEach(entry -> {
			TableColumn<String, String> entryColumn = new TableColumn<>(entry.getKey());
			entryColumn.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<String,String>, ObservableValue<String>>() {

				@Override
				public ObservableValue<String> call(CellDataFeatures<String, String> param) {
					return new ObservableValueBase<String>() {
						@Override
						public String getValue() {
							Log log = entry.getValue().get(param.getValue());
							if(log == null) return "";
							else return log.getDuration()+"";
						}
					};
				}
			});
			LogTable.getColumns().add(entryColumn);
		});
		
		
//		TagColumn.setCellFactory(new Callback<CellDataFeatures<String, String>, String>() {
//
//			@Override
//			public String call(CellDataFeatures<String, String> param) {
//				return param.getValue();
//			}
//		});
	}
	
	public void openLoggerWidget() {
		openWidgetView();
	}

}
