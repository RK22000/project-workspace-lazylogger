package application.control;

import java.time.LocalDate;
import java.time.temporal.TemporalAdjusters;

import application.model.DayLog;
import application.model.Log;
import application.model.Logger2;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.beans.value.ObservableValueBase;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableColumn.CellDataFeatures;
import javafx.scene.control.TablePosition;
import javafx.scene.control.TableView;
import javafx.util.Callback;

public class LoggerReportController2 extends GeneralController{
	@FXML TableColumn<String, String> TagColumn;
	@FXML TableView<String> LogTable;
	private LocalDate firstDay;
	private Logger2 peerMentorLogger;
	@FXML Label LogDisplay;
	
	@FXML
	private void initialize() {
		peerMentorLogger = getLogger();
		
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
		
		firstDay = LocalDate.parse("2021-11-02");
		firstDay = LocalDate.of(firstDay.getYear(), firstDay.getMonthValue(), 1);
		setColumns();
		
		LogTable.getFocusModel().focusedCellProperty().addListener(
				(ChangeListener<? super TablePosition>) new ChangeListener<TablePosition<String, String>>() {

					@Override
					public void changed(ObservableValue<? extends TablePosition<String, String>> observable,
							TablePosition<String, String> oldValue, TablePosition<String, String> newValue) {
						
						int row = newValue.getRow();
						int col = newValue.getColumn();
						
						
						LogDisplay.setText(getCellLogString(row, col));
						
					}
					
				});
	}
	
	private String getCellLogString(int row, int col) {
		Log log=null;
		if (LogTable.getItems().size() > row &&
				LogTable.getColumns().size() > col) {
			String date = LogTable.getColumns().get(col).getText().substring(0, 10);
			String activity = LogTable.getItems().get(row);
			
			DayLog dayLog = peerMentorLogger.get(date); 
			if(dayLog == null) return "Empty day";
			log = dayLog.get(activity);
			if(log == null) return "Empty Log";		
			
		}
		
		
		return log.toString();
	}
	
	private void setColumns() {
		TableColumn<String, ?> firstColumn = LogTable.getColumns().get(0);
		LogTable.getColumns().clear();
		LogTable.getColumns().add(firstColumn);
		
		LocalDate lastDay = firstDay.with(TemporalAdjusters.firstDayOfNextMonth());
		
		for(LocalDate date = firstDay; date.isBefore(lastDay); date = date.plusDays(1)) {
			final String finalDate = date.toString();
			TableColumn<String, String> entryColumn = new TableColumn<>(date +"\n"+ date.getDayOfWeek());
			entryColumn.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<String,String>, ObservableValue<String>>() {
				
				@Override
				public ObservableValue<String> call(CellDataFeatures<String, String> param) {
					return new ObservableValueBase<String>() {
						@Override
						public String getValue() {
							DayLog dayLog = peerMentorLogger.get(finalDate); 
							if(dayLog == null) return "";
							Log log = dayLog.get(param.getValue());
							if(log == null) return "";
							else return log.getDuration()+"";
						}
					};
				}
			});
			LogTable.getColumns().add(entryColumn);
		}
		
		/*
		peerMentorLogger.entrySet().forEach(entry -> {
			TableColumn<String, String> entryColumn = new TableColumn<>(entry.getKey()+"\n"+entry.getValue().getDay().getDayOfWeek());
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
		*/
	}
	
	public void openLoggerWidget() {
		openWidgetView();
	}

}
