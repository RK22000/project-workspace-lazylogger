package application.control;

import java.time.LocalDate;
import java.time.temporal.TemporalAdjusters;

import application.model.DayLog;
import application.model.Log;
import application.model.Logger2;
import application.model.PeerMentorLog;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.beans.value.ObservableValueBase;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableColumn.CellDataFeatures;
import javafx.scene.control.TableColumn.CellEditEvent;
import javafx.scene.control.TablePosition;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.util.Callback;
import javafx.util.converter.IntegerStringConverter;

public class LoggerReportController2 extends GeneralController {
	@FXML
	TableColumn<String, String> TagColumn;
	@FXML
	TableView<String> LogTable;
	private LocalDate firstDay;
	private Logger2 peerMentorLogger;
	@FXML
	Label LogDisplay;

	@FXML
	private void initialize() {
		peerMentorLogger = getLogger();

		ObservableList<String> activities = FXCollections.observableList(peerMentorLogger.getValidActivities());
		activities.add("Total");

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

		LogTable.setEditable(true);
	}

	private String getCellLogString(int row, int col) {
		Log log = null;
		if (LogTable.getItems().size() > row && LogTable.getColumns().size() > col) {
			String date = LogTable.getColumns().get(col).getText().substring(0, 10);
			String activity = LogTable.getItems().get(row);

			DayLog dayLog = peerMentorLogger.get(date);
			if (dayLog == null)
				return "Empty day";
			log = dayLog.get(activity);
			if (log == null)
				return "Empty Log";

		}

		return log.toString();
	}

	private void setColumns() {
		TableColumn<String, ?> firstColumn = LogTable.getColumns().get(0);
		LogTable.getColumns().clear();
		LogTable.getColumns().add(firstColumn);

		LocalDate lastDay = firstDay.with(TemporalAdjusters.firstDayOfNextMonth());

		for (LocalDate date = firstDay; date.isBefore(lastDay); date = date.plusDays(1)) {
			final LocalDate colomnDate = date;
			TableColumn<String, Integer> entryColumn = new TableColumn<>(date + "\n" + date.getDayOfWeek());
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
					if (dayLog != null) {
						log = dayLog.get(event.getRowValue());
						if (log != null) {
							if (event.getNewValue() == null || event.getNewValue() == 0)
								dayLog.remove(log.getJobActivity());
							else
								log.setDuration(event.getNewValue());
							saveLogger(peerMentorLogger);
							return;
						}
					}
					
					if (event.getNewValue() == null || event.getNewValue() == 0) return;

					log = PeerMentorLog.createLog(event.getRowValue(), event.getNewValue(), "");
					log.setLogDate(colomnDate);
					peerMentorLogger.add(log);
					saveLogger(peerMentorLogger);
					

				}
			});
			entryColumn.setSortable(false);
			LogTable.getColumns().add(entryColumn);

			// entryColumn.addEventHandler(MouseEvent.MOUSE_CLICKED, EventHandler<E>);
		}

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

}
