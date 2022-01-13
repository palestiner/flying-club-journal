package org.palestiner.flyingclubjournal.actions;


import io.jmix.ui.Dialogs;
import io.jmix.ui.action.ActionType;
import io.jmix.ui.app.inputdialog.DialogActions;
import io.jmix.ui.app.inputdialog.DialogOutcome;
import io.jmix.ui.app.inputdialog.InputDialog;
import io.jmix.ui.app.inputdialog.InputParameter;
import io.jmix.ui.component.GroupTable;
import io.jmix.ui.icon.Icons;
import io.jmix.ui.icon.JmixIcon;
import io.jmix.ui.meta.StudioAction;
import io.jmix.ui.model.CollectionContainer;
import io.jmix.ui.screen.FrameOwner;
import io.jmix.ui.screen.MessageBundle;
import io.jmix.uiexport.action.ExportAction;
import io.jmix.uiexport.exporter.excel.ExcelExporter;
import org.palestiner.flyingclubjournal.entity.Cadet;
import org.palestiner.flyingclubjournal.entity.MoneyAccounting;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;

import javax.annotation.Nullable;
import java.util.List;
import java.util.stream.Collectors;

@StudioAction(target = "io.jmix.ui.component.ListComponent", description = "Export selected entities to Excel")
@ActionType(CustomExcelAction.ID)
public class CustomExcelAction extends ExportAction {
    private static final Logger log = org.slf4j.LoggerFactory.getLogger(CustomExcelAction.class);
    private Dialogs dialogs;
    private GroupTable<MoneyAccounting> moneyAccountingsTable;
    private MessageBundle messageBundle;
    private FrameOwner owner;
    private CollectionContainer<MoneyAccounting> moneyAccountingsDc;

    public static final String ID = "customExcelExport";

    @Autowired
    protected Icons icons;

    public CustomExcelAction(String id) {
        this(id, null);
    }

    public CustomExcelAction() {
        this(ID);
    }

    public CustomExcelAction(String id, String shortcut) {
        super(id, shortcut);
    }

    @Override
    protected void execute() {
        dialogs
                .createInputDialog(owner)
                .withCaption(messageBundle.getMessage("report.dialogCaption"))
                .withParameter(InputParameter
                        .entityParameter("entity", Cadet.class)
                        .withRequired(true)
                        .withRequiredMessage(messageBundle.getMessage("report.NotNull"))
                        .withCaption(messageBundle.getMessage("report.caption")))
                .withActions(DialogActions.OK_CANCEL, inputDialogResult -> {
                    if (inputDialogResult.closedWith(DialogOutcome.OK)) {
                        Cadet cadet = inputDialogResult.getValue("entity");
                        List<MoneyAccounting> items = moneyAccountingsDc
                                .getItems()
                                .stream()
                                .filter(moneyAccounting -> moneyAccounting.getCadet().equals(cadet))
                                .collect(Collectors.toList());
                        log.info(String.valueOf(items));
                        moneyAccountingsTable.setSelected(items);
                        setTarget(moneyAccountingsTable);
                        super.execute();
                    }
                })
                .build()
                .show();
    }

    public void customExecute() {
        execute();
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) {
        super.setApplicationContext(applicationContext);
        withExporter(ExcelExporter.class);
    }

    @Override
    public String getIcon() {
        return icons.get(JmixIcon.EXCEL_ACTION);
    }


    public GroupTable<MoneyAccounting> getMoneyAccountingsTable() {
        return moneyAccountingsTable;
    }

    public void setMoneyAccountingsTable(GroupTable<MoneyAccounting> moneyAccountingsTable) {
        this.moneyAccountingsTable = moneyAccountingsTable;
    }

    public Dialogs getDialogs() {
        return dialogs;
    }

    public void setDialogs(Dialogs dialogs) {
        this.dialogs = dialogs;
    }

    public void setMessageBundle(MessageBundle messageBundle) {
        this.messageBundle = messageBundle;
    }

    public void setOwner(FrameOwner owner) {
        this.owner = owner;
    }

    public void setMoneyAccountingsDc(CollectionContainer<MoneyAccounting> moneyAccountingsDc) {
        this.moneyAccountingsDc = moneyAccountingsDc;
    }
}
