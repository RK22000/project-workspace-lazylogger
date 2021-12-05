package application.control;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.TemporalAdjusters;
import java.util.HashMap;
import java.util.Map;

import application.model.DayLog;
import application.model.Log;
import application.model.Logger2;
import application.model.PeerMentorLog;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.beans.value.ObservableValueBase;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableColumn.CellDataFeatures;
import javafx.scene.control.TableColumn.CellEditEvent;
import javafx.scene.control.TablePosition;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.util.Callback;
import javafx.util.StringConverter;
import javafx.util.converter.IntegerStringConverter;

public class LoggerReportController2 extends GeneralController {
	@FXML
	TableColumn<String, String> TagColumn;
	@FXML
	TableView<String> LogTable;
	//private LocalDate focusDay;
	private Logger2 peerMentorLogger;
	@FXML
	Label LogDisplay;

	private final String TOTAL = "Total";
	@FXML DatePicker focusPicker;
	private final String DATE_FORMAT = "d MMM, uuuu";
	private StringConverter<LocalDate> dateStringConverter = new StringConverter<LocalDate>() {

		private DateTimeFormatter dateTimeFormatter=DateTimeFormatter.ofPattern(DATE_FORMAT);
		
		
		@Override
		public String toString(LocalDate localDate) {
			if(localDate==null)
	            return "";
	        return dateTimeFormatter.format(localDate);
		}


		@Override
		public LocalDate fromString(String dateString) {
			if(dateString==null || dateString.trim().isEmpty())
	        {
	            return null;
	        }
	        return LocalDate.parse(dateString,dateTimeFormatter);
		}
		
	};
	@FXML TextArea commentBox;
	@FXML Button commentButton;

	@FXML
	private void initialize() {
		peerMentorLogger = getLogger();

		ObservableList<String> activities = FXCollections.observableList(peerMentorLogger.getValidActivities());

		TagColumn.setCellValueFactory(
				new Callback<TableColumn.CellDataFeatures<String, String>, ObservableValue<String>>() {

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
		TagColumn.setSortable(false);
		TagColumn.setCellFactory(TextFieldTableCell.forTableColumn());

		// LogTable.setItems(activities);
		LogTable.getItems().addAll(activities);
		LogTable.getItems().add(TOTAL);

		System.out.println(TagColumn.getPrefWidth());

		focusPicker.setValue(LocalDate.now());
		focusPicker.setConverter(dateStringConverter);
		//focusDay = focusPicker.getValue();
		setColumns();

		LogTable.getFocusModel().focusedCellProperty().addListener(
				(ChangeListener<? super TablePosition>) new ChangeListener<TablePosition<String, String>>() {

					@Override
					public void changed(ObservableValue<? extends TablePosition<String, String>> observable,
							TablePosition<String, String> oldValue, TablePosition<String, String> newValue) {

						int row = newValue.getRow();
						int col = newValue.getColumn();
						if (col > 0 && col < 8 && row < LogTable.getItems().size()-1)
							//LogDisplay.setText(getCellLogString(row, col));
							displayLog(getCellLog(row, col));
						else 
							displayLog(null);

					}

				});
		displayLog(null);

		LogTable.setEditable(true);
	}

		
	private Log getCellLog(int row, int col) {
		Log log = null;
		if (LogTable.getItems().size() > row && LogTable.getColumns().size() > col) {
			String stringDate = LogTable.getColumns().get(col).getText().substring(0, 12);
			if(stringDate.charAt(11) == '\n') stringDate = stringDate.substring(0, 11);
			System.out.println("Trying to parse " + stringDate);
			LocalDate localDate = dateStringConverter.fromString(stringDate);
			String date = localDate.toString();
			String activity = LogTable.getItems().get(row);

			DayLog dayLog = peerMentorLogger.get(date);
			if (dayLog == null) {
				System.out.println("Empty day");
				return null;
			}
			log = dayLog.get(activity);
			if (log == null) {
				System.out.println("Empty Log");
				return null;
			}

		}

		return log;
	}

	private void setColumns() {
		TableColumn<String, ?> firstColumn = LogTable.getColumns().get(0);
		LogTable.getColumns().clear();
		LogTable.getColumns().add(firstColumn);

		LocalDate firstDay;
		if(!focusPicker.getValue().getDayOfWeek().equals(DayOfWeek.SUNDAY)) {
			firstDay = focusPicker.getValue().with(TemporalAdjusters.previous(DayOfWeek.SUNDAY));
		} else {
			firstDay = focusPicker.getValue();
		}
		LocalDate lastDay = focusPicker.getValue().with(TemporalAdjusters.next(DayOfWeek.SUNDAY));

		for (LocalDate date = firstDay; date.isBefore(lastDay); date = date.plusDays(1)) {
			// First set up the Column for the particular day
			final LocalDate colomnDate = date;
			TableColumn<String, Integer> entryColumn = new TableColumn<>(dateStringConverter.toString(date) + "\n" + date.getDayOfWeek());
			entryColumn.setCellValueFactory(
					new Callback<TableColumn.CellDataFeatures<String, Integer>, ObservableValue<Integer>>() {

						@Override
						public ObservableValue<Integer> call(CellDataFeatures<String, Integer> param) {
							return new ObservableValueBase<Integer>() {
								@Override
								public Integer getValue() {
									DayLog dayLog = peerMentorLogger.get(colomnDate.toString());
									if (dayLog == null)
										return null;
									if (param.getValue().equals(TOTAL)) {
										Integer duration = null;
										duration = dayLog.getTotalMinutes();
										return (duration == 0) ? null : duration;
									}
									Log log = dayLog.get(param.getValue());
									if (log == null)
										return null;
									else
										return log.getDuration();
								}
							};
						}
					});

			entryColumn.setCellFactory(TextFieldTableCell.forTableColumn(new IntegerStringConverter()));

			entryColumn.setOnEditCommit(new EventHandler<TableColumn.CellEditEvent<String, Integer>>() {

				@Override
				public void handle(CellEditEvent<String, Integer> event) {
					Log log = null;
					DayLog dayLog = peerMentorLogger.get(colomnDate.toString());
					// This if acts on existing Logs
					if (dayLog != null) {
						log = dayLog.get(event.getRowValue());
						if (log != null) {
							if (event.getNewValue() != null && event.getNewValue() != 0)
								log.setDuration(event.getNewValue());
							else {
								dayLog.remove(log.getJobActivity());
							}
							saveLogger(peerMentorLogger);
							LogTable.refresh();
							return;
						}
					}

					// This if deals with no log before and no log after, basically no changes
					if (event.getNewValue() == null || event.getNewValue() == 0) {
						LogTable.refresh();
						return;
					}

					// This section deals with Log creation for when Log is made in report
					log = PeerMentorLog.createLog(event.getRowValue(), event.getNewValue(), "");
					if (log == null) {LogTable.refresh(); return;}
					log.setLogDate(colomnDate);
					peerMentorLogger.add(log);
					LogTable.refresh();
					saveLogger(peerMentorLogger);

				}
			});
			entryColumn.setSortable(false);
			LogTable.getColumns().add(entryColumn);

		}

		TableColumn<String, Integer> totalColumn = new TableColumn<>(TOTAL);
		totalColumn.setCellValueFactory(
				new Callback<TableColumn.CellDataFeatures<String, Integer>, ObservableValue<Integer>>() {

					@Override
					public ObservableValue<Integer> call(CellDataFeatures<String, Integer> param) {
						Map<String, Integer> totalMap = new HashMap<>();
						LogTable.getItems().forEach(item -> totalMap.put(item, 0));

						for (LocalDate date = firstDay; date.isBefore(lastDay); date = date.plusDays(1)) {
							DayLog columnDay = peerMentorLogger.get(date.toString());
							if (columnDay == null)
								continue;
							columnDay.entrySet().forEach(entry -> totalMap.put(entry.getKey(),
									totalMap.get(entry.getKey()) + entry.getValue().getDuration()));
							totalMap.put(TOTAL, totalMap.get(TOTAL) + columnDay.getTotalMinutes());
						}

						return new ObservableValueBase<Integer>() {

							@Override
							public Integer getValue() {
								Integer total = totalMap.get(param.getValue());
								return (total == 0) ? null : total;
							}
						};
					}
				});

		LogTable.getColumns().add(totalColumn);

		/*
		 * peerMentorLogger.entrySet().forEach(entry -> { TableColumn<String, String>
		 * entryColumn = new
		 * TableColumn<>(entry.getKey()+"\n"+entry.getValue().getDay().getDayOfWeek());
		 * entryColumn.setCellValueFactory(new
		 * Callback<TableColumn.CellDataFeatures<String,String>,
		 * ObservableValue<String>>() {
		 * 
		 * @Override public ObservableValue<String> call(CellDataFeatures<String,
		 * String> param) { return new ObservableValueBase<String>() {
		 * 
		 * @Override public String getValue() { Log log =
		 * entry.getValue().get(param.getValue()); if(log == null) return ""; else
		 * return log.getDuration()+""; } }; } });
		 * LogTable.getColumns().add(entryColumn); });
		 */
	}

	public void openLoggerWidget() {
		openWidgetView();
	}

	@FXML public void focusOnWeek() {
		//focusDay = focusPicker.getValue();
		setColumns();
	}

	@FXML public void focusToPrevWeek() {
		LocalDate focusDate = focusPicker.getValue();
		focusPicker.setValue(focusDate.with(TemporalAdjusters.previous(focusDate.getDayOfWeek())));
		focusOnWeek();
	}

	@FXML public void focusToNextWeek() {
		LocalDate focusDate = focusPicker.getValue();
		focusPicker.setValue(focusDate.with(TemporalAdjusters.next(focusDate.getDayOfWeek())));
		focusOnWeek();
	}
	
	private void displayLog(Log log) {
		if(log == null) {
			LogDisplay.setText("");
			commentBox.setVisible(false);
			commentButton.setVisible(false);
			return;
		}
		
		// Log is not null
		String display = dateStringConverter.toString(log.getLogDate()) + "\n" + 
				log.getDuration() + " minutes of " + log.getJobActivity();
		LogDisplay.setText(display);
		
		commentBox.setText(log.getComment());
		commentBox.setVisible(true);
		
		commentBox.textProperty().addListener(new ChangeListener<String>() {

			@Override
			public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
				commentButton.setVisible(!newValue.trim().equals(log.getComment()));
			}
		});
		
		commentButton.setOnMouseClicked(event -> {
			log.setComment(commentBox.getText());
			saveLogger(peerMentorLogger);
			commentButton.setVisible(false);
		});
	}

}
