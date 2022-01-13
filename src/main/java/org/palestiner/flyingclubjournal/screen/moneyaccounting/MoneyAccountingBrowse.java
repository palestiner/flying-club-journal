package org.palestiner.flyingclubjournal.screen.moneyaccounting;

import io.jmix.core.DataManager;
import io.jmix.core.SaveContext;
import io.jmix.ui.Actions;
import io.jmix.ui.Dialogs;
import io.jmix.ui.UiComponents;
import io.jmix.ui.action.Action;
import io.jmix.ui.action.list.RefreshAction;
import io.jmix.ui.app.inputdialog.DialogActions;
import io.jmix.ui.app.inputdialog.DialogOutcome;
import io.jmix.ui.app.inputdialog.InputDialog;
import io.jmix.ui.app.inputdialog.InputParameter;
import io.jmix.ui.component.Button;
import io.jmix.ui.component.Component;
import io.jmix.ui.component.GroupTable;
import io.jmix.ui.component.LinkButton;
import io.jmix.ui.model.CollectionContainer;
import io.jmix.ui.screen.*;
import io.jmix.uiexport.exporter.excel.ExcelExporter;
import org.palestiner.flyingclubjournal.actions.CustomExcelAction;
import org.palestiner.flyingclubjournal.entity.Cadet;
import org.palestiner.flyingclubjournal.entity.MoneyAccounting;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;

import javax.inject.Named;
import java.util.stream.Collectors;

@UiController("MoneyAccounting.browse")
@UiDescriptor("money-accounting-browse.xml")
@LookupComponent("moneyAccountingsTable")
public class MoneyAccountingBrowse extends StandardLookup<MoneyAccounting> {
    @Autowired
    private UiComponents uiComponents;
    @Autowired
    private Dialogs dialogs;
    @Autowired
    private MessageBundle messageBundle;
    @Autowired
    private DataManager dataManager;
    @Named("moneyAccountingsTable.refresh")
    private RefreshAction moneyAccountingsTableRefresh;
    @Autowired
    private GroupTable<MoneyAccounting> moneyAccountingsTable;
    @Autowired
    private CollectionContainer<MoneyAccounting> moneyAccountingsDc;
    @Autowired
    private Actions actions;
    @Autowired
    private ApplicationContext applicationContext;
    private CustomExcelAction customExcelAction;

    @Install(to = "moneyAccountingsTable.paid", subject = "columnGenerator")
    private Component moneyAccountingsTablePaidColumnGenerator(MoneyAccounting moneyAccounting) {
        LinkButton linkButton = uiComponents.create(LinkButton.class);
        linkButton.setCaption(String.valueOf(moneyAccounting.getPaid()));
        linkButton.addClickListener(clickEvent -> {
            dialogs
                    .createInputDialog(this)
                    .withCaption(messageBundle.getMessage("paidInput.dialogCaption"))
                    .withParameter(InputParameter
                            .doubleParameter("paid")
                            .withDefaultValue(moneyAccounting.getPaid())
                            .withRequired(true)
                            .withRequiredMessage(messageBundle.getMessage("paidInput.NotNull"))
                            .withCaption(messageBundle.getMessage("paidInput.caption")))
                    .withActions(DialogActions.OK_CANCEL, inputDialogResult -> {
                        if (inputDialogResult.closedWith(DialogOutcome.OK)) {
                            Double paid = inputDialogResult.getValue("paid");
                            moneyAccounting.setPaid(paid != null && paid >= 0 ? paid : moneyAccounting.getPaid());
                            dataManager.save(new SaveContext().saving(moneyAccounting));
                            moneyAccountingsTableRefresh.execute();
                        }
                    })
                    .build()
                    .show();
            linkButton.setCaption(String.valueOf(moneyAccounting.getPaid()));
        });
        return linkButton;
    }

    @Install(to = "moneyAccountingsTable.total", subject = "columnGenerator")
    private Component moneyAccountingsTableTotalColumnGenerator(MoneyAccounting moneyAccounting) {
        LinkButton linkButton = uiComponents.create(LinkButton.class);
        linkButton.setCaption(String.valueOf(moneyAccounting.getTotal()));
        linkButton.setStyleName(moneyAccounting.getTotal() > 0 ? "red" : "green");
        return linkButton;
    }

    @Subscribe
    public void onInit(InitEvent event) {
        customExcelAction = actions.create(CustomExcelAction.class);
        customExcelAction.setTarget(moneyAccountingsTable);
        customExcelAction.setDialogs(dialogs);
        customExcelAction.setMoneyAccountingsDc(moneyAccountingsDc);
        customExcelAction.setMessageBundle(messageBundle);
        customExcelAction.setOwner(this);
        customExcelAction.setApplicationContext(applicationContext);
        customExcelAction.setMoneyAccountingsTable(moneyAccountingsTable);
        customExcelAction.setTableExporter(applicationContext.getBean(ExcelExporter.class));
    }



    @Subscribe("moneyAccountingsTable.report")
    public void onMoneyAccountingsTableReport(Action.ActionPerformedEvent event) {
        customExcelAction.customExecute();
    }
}
