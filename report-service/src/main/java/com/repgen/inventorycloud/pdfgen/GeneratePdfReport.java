package com.repgen.inventorycloud.pdfgen;

import com.itextpdf.text.Chunk;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.FontFactory;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.TabSettings;
import com.itextpdf.text.pdf.PdfContentByte;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import com.itextpdf.text.pdf.draw.LineSeparator;
import com.repgen.inventorycloud.modal.StockMovementDetails;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.util.List;

public class GeneratePdfReport {
	private static final Logger logger = LoggerFactory.getLogger(GeneratePdfReport.class);

    public static ByteArrayInputStream citiesReport(StockMovementDetails stockMovementDetails) throws FileNotFoundException {
    	
  
        Document document = new Document();
        ByteArrayOutputStream out = new ByteArrayOutputStream();

        try {

            @SuppressWarnings("unused")
            PdfWriter pdfWriter = PdfWriter.getInstance(document, new FileOutputStream("StockMovementReport.pdf"));

            Font titleFont1 = FontFactory.getFont(FontFactory.HELVETICA_BOLD,25f);
            Paragraph Heading = new Paragraph("Stock Movement Report",titleFont1);
            Heading.setAlignment(Element.ALIGN_CENTER);
            
            Paragraph lineSpace = new Paragraph(" ");
            lineSpace.setAlignment(Element.ALIGN_LEFT);
            
            Font titleFont2 = FontFactory.getFont(FontFactory.HELVETICA_BOLD,11f);     
            Paragraph itemName = new Paragraph("Item Name : "+ stockMovementDetails.getItemName(),titleFont2);
            itemName.setAlignment(Element.ALIGN_LEFT);
            Paragraph brandName = new Paragraph("Brand Name : "+ stockMovementDetails.getBrandName(),titleFont2);
            brandName.setAlignment(Element.ALIGN_LEFT);
            Paragraph uomName = new Paragraph("UOM Name : "+ stockMovementDetails.getUomName(),titleFont2);
            uomName.setAlignment(Element.ALIGN_LEFT);
            
            Font titleFont3 = FontFactory.getFont(FontFactory.HELVETICA_BOLD,18f);     
            Paragraph subTopic = new Paragraph("Open Stock",titleFont3);
            subTopic.setAlignment(Element.ALIGN_LEFT);
            
            Paragraph subTopicIssue = new Paragraph("Issue Transaction logs",titleFont3);
            subTopicIssue.setAlignment(Element.ALIGN_LEFT);
            
            Paragraph subTopicRecived = new Paragraph("Recived Transaction logs",titleFont3);
            subTopicRecived.setAlignment(Element.ALIGN_LEFT);
            
            Paragraph transactionTopic = new Paragraph("Date"+ Chunk.TAB,titleFont2);
            transactionTopic.add("Quantity");
            transactionTopic.setAlignment(Element.ALIGN_LEFT);
            
            Font titleFont4 = FontFactory.getFont(FontFactory.HELVETICA,10f);   
            PdfWriter.getInstance(document, out);
            document.open();
            document.add(Heading);
            document.add(new LineSeparator(0.5f, 100, null, 0, -5));
            document.add(lineSpace);
            document.add(lineSpace);
            document.add(itemName);
            document.add(lineSpace);
            document.add(brandName);
            document.add(lineSpace);
            document.add(uomName);
            document.add(lineSpace);
            document.add(new LineSeparator(0.5f, 100, null, 0, -5));
            document.add(subTopic);
            document.add(lineSpace);
            document.add(createParagraphWithTab("Date"," ","Quantity",titleFont2));
            document.add(createParagraphWithTab(stockMovementDetails.getOpenStockDate().toString()
            		," ",stockMovementDetails.getOpenStockQuantity().toString(),titleFont4));
            
            document.add(lineSpace);
            document.add(new LineSeparator(0.5f, 100, null, 0, -5));
            document.add(subTopicIssue);
            document.add(lineSpace);
            document.add(createParagraphWithTab("Date"," ","Quantity",titleFont2));
            for (int i = 0; i < stockMovementDetails.getIssueLog().size(); i++) {
            	document.add(createParagraphWithTab(stockMovementDetails.getIssueLog().get(i).getDateTime().toString()
            			," ",stockMovementDetails.getIssueLog().get(i).getQuantity().toString(),titleFont4));
			}
            document.add(createParagraphWithTab(" "," ","______",titleFont4));
            document.add(createParagraphWithTab(" ","Total issue transactions :",stockMovementDetails.getTotalIssueQuantity().toString(),titleFont4));
            document.add(createParagraphWithTab(" ","No. of issue transactions :",stockMovementDetails.getIssueCount().toString(),titleFont4));
            document.add(createParagraphWithTab(" "," ","______",titleFont4));
            document.add(createParagraphWithTab(" ","Average issue quantity :",stockMovementDetails.getAverageIssueQuantity().toString(),titleFont4));

            
            document.add(lineSpace);
            document.add(new LineSeparator(0.5f, 100, null, 0, -5));
            document.add(subTopicRecived);
            document.add(lineSpace);
            document.add(createParagraphWithTab("Date"," ","Quantity",titleFont2));
            for (int i = 0; i < stockMovementDetails.getRevivedLog().size(); i++) {
            	document.add(createParagraphWithTab(stockMovementDetails.getRevivedLog().get(i).getDateTime().toString()
            			," ",stockMovementDetails.getRevivedLog().get(i).getQuantity().toString(),titleFont4));
			}
            document.add(createParagraphWithTab(" "," ","______",titleFont4));
            document.add(createParagraphWithTab(" ","Total recived transactions :",stockMovementDetails.getTotalRevivedQuantity().toString(),titleFont4));
            document.add(createParagraphWithTab(" ","No. of recived transactions :",stockMovementDetails.getRevivedCount().toString(),titleFont4));
            document.add(createParagraphWithTab(" "," ","______",titleFont4));
            document.add(createParagraphWithTab(" ","Average recived quantity :",stockMovementDetails.getAverageRevivedQuantity().toString(),titleFont4));

            document.add(lineSpace);
            document.add(new LineSeparator(0.5f, 100, null, 0, -5));
            document.add(createParagraphWithTab(" ","Final Remaining Stock :",stockMovementDetails.getStockRemaining().toString(),titleFont4));

            
            document.close();

        } catch (DocumentException ex) {

            logger.error("Error occurred: {0}", ex);
        }

        return new ByteArrayInputStream(out.toByteArray());
    }
    
    public static Paragraph createParagraphWithTab(String key, String value1, String value2, Font font) {
        Paragraph p = new Paragraph();
        p.setFont(font);
        p.setTabSettings(new TabSettings(200f));
        p.add(key);
        p.add(value1);
        p.add(Chunk.TABBING);
        p.add(value2);
        
        return p;
    }
}
