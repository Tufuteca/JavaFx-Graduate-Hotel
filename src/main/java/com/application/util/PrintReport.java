package com.application.util;

import com.application.controller.output.alert.NotificationsController;
import com.application.controller.output.report.ClientReport;
import com.application.controller.output.report.ReportRooms;
import com.itextpdf.kernel.font.PdfFontFactory;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.element.Table;
import com.itextpdf.layout.properties.TextAlignment;
import com.itextpdf.layout.properties.UnitValue;
import com.itextpdf.layout.properties.VerticalAlignment;
import com.itextpdf.kernel.font.PdfFont;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.scene.control.TableView;
import javafx.scene.control.TableColumn;
import org.springframework.stereotype.Controller;

import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.time.LocalDate;
import java.util.List;

@Controller
public class PrintReport {

    private final NotificationsController notificationsController;

    public PrintReport(NotificationsController notificationsController) {
        this.notificationsController = notificationsController;
    }

    public void printReportClients(TableView<ClientReport.ClientStats> reportTableClients, String subtitlePg) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("PDF files (*.pdf)", "*.pdf"));
        File file = fileChooser.showSaveDialog(new Stage());
        if (file != null) {
            try {
                PdfWriter writer = new PdfWriter(file.getAbsolutePath());
                PdfDocument pdf = new PdfDocument(writer);
                Document document = new Document(pdf);

                // Загрузка шрифта
                String fontPath = getClass().getResource("/fonts/freesans.ttf").toURI().getPath();
                PdfFont font = PdfFontFactory.createFont(fontPath, "CP1251");

                // Заголовок
                Paragraph title = new Paragraph("Отчет о клиентах")
                        .setFont(font)
                        .setTextAlignment(TextAlignment.CENTER)
                        .setFontSize(20);
                document.add(title);

                // Подзаголовок
                Paragraph subtitle = new Paragraph(subtitlePg)
                        .setFont(font)
                        .setTextAlignment(TextAlignment.CENTER)
                        .setFontSize(14);
                document.add(subtitle);

                // Дата составления
                Paragraph date = new Paragraph("Дата составления: " + LocalDate.now())
                        .setFont(font)
                        .setTextAlignment(TextAlignment.RIGHT)
                        .setFontSize(10);
                document.add(date);

                // Таблица
                float[] columnWidths = new float[reportTableClients.getColumns().size()];
                for (int i = 0; i < columnWidths.length; i++) {
                    columnWidths[i] = 1; // ширина всех колонок будет равномерной
                }
                Table table = new Table(columnWidths);
                table.setWidth(UnitValue.createPercentValue(100)); // Устанавливаем ширину таблицы на 100% ширины страницы

                for (TableColumn<ClientReport.ClientStats, ?> col : reportTableClients.getColumns()) {
                    table.addHeaderCell(new Paragraph(col.getText()).setFont(font));
                }
                List<ClientReport.ClientStats> items = reportTableClients.getItems();
                for (ClientReport.ClientStats item : items) {
                    for (TableColumn<ClientReport.ClientStats, ?> col : reportTableClients.getColumns()) {
                        table.addCell(new Paragraph(col.getCellData(item).toString()).setFont(font));
                    }
                }
                document.add(table);

                // Нумерация страниц
                int numberOfPages = pdf.getNumberOfPages();
                for (int i = 1; i <= numberOfPages; i++) {
                    document.showTextAligned(new Paragraph(String.format("Страница %s из %s", i, numberOfPages)).setFont(font),
                            559, 806, i, TextAlignment.RIGHT, VerticalAlignment.TOP, 0);
                }

                document.close();

                notificationsController.printSuccessfully();
            } catch (IOException | URISyntaxException e) {
                e.printStackTrace();
            }
        }
    }

    public void printReportBooking(TableView<ReportRooms.BookingStats> tableStatRooms, String subtitlePg) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("PDF files (*.pdf)", "*.pdf"));
        File file = fileChooser.showSaveDialog(new Stage());
        if (file != null) {
            try {
                PdfWriter writer = new PdfWriter(file.getAbsolutePath());
                PdfDocument pdf = new PdfDocument(writer);
                Document document = new Document(pdf);

                // Загрузка шрифта
                String fontPath = getClass().getResource("/fonts/freesans.ttf").toURI().getPath();
                PdfFont font = PdfFontFactory.createFont(fontPath, "CP1251");

                // Заголовок
                Paragraph title = new Paragraph("Отчет о номерах и бронях")
                        .setFont(font)
                        .setTextAlignment(TextAlignment.CENTER)
                        .setFontSize(20);
                document.add(title);

                // Подзаголовок
                Paragraph subtitle = new Paragraph(subtitlePg)
                        .setFont(font)
                        .setTextAlignment(TextAlignment.CENTER)
                        .setFontSize(14);
                document.add(subtitle);

                // Дата составления
                Paragraph date = new Paragraph("Дата составления: " + LocalDate.now())
                        .setFont(font)
                        .setTextAlignment(TextAlignment.RIGHT)
                        .setFontSize(10);
                document.add(date);

                // Таблица
                float[] columnWidths = new float[tableStatRooms.getColumns().size()];
                for (int i = 0; i < columnWidths.length; i++) {
                    columnWidths[i] = 1; // ширина всех колонок будет равномерной
                }
                Table table = new Table(columnWidths);
                table.setWidth(UnitValue.createPercentValue(100)); // Устанавливаем ширину таблицы на 100% ширины страницы

                for (TableColumn<ReportRooms.BookingStats, ?> col : tableStatRooms.getColumns()) {
                    table.addHeaderCell(new Paragraph(col.getText()).setFont(font));
                }
                List<ReportRooms.BookingStats> items = tableStatRooms.getItems();
                for (ReportRooms.BookingStats item : items) {
                    for (TableColumn<ReportRooms.BookingStats, ?> col : tableStatRooms.getColumns()) {
                        table.addCell(new Paragraph(col.getCellData(item).toString()).setFont(font));
                    }
                }
                document.add(table);

                // Нумерация страниц
                int numberOfPages = pdf.getNumberOfPages();
                for (int i = 1; i <= numberOfPages; i++) {
                    document.showTextAligned(new Paragraph(String.format("Страница %s из %s", i, numberOfPages)).setFont(font),
                            559, 806, i, TextAlignment.RIGHT, VerticalAlignment.TOP, 0);
                }

                document.close();

                notificationsController.printSuccessfully();
            } catch (IOException | URISyntaxException e) {
                e.printStackTrace();
            }
        }
    }
}
