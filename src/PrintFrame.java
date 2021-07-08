package src;


import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.Rectangle2D;
import java.awt.print.Book;
import java.awt.print.PageFormat;
import java.awt.print.Printable;
import java.awt.print.PrinterJob;

public class PrintFrame {


    //--- Private instances declarations
    private final static int POINTS_PER_INCH = 72;
    public PrintFrame() {

        //--- Create a new PrinterJob object
        PrinterJob printJob = PrinterJob.getPrinterJob();

        //--- Create a new book to add pages to
        Book book = new Book();

        //--- Add the cover page using the default page format for this print
        // job
        book.append(new IntroPage(), printJob.defaultPage());

        //--- Add the document page using a landscape page format
        PageFormat documentPageFormat = new PageFormat();
        documentPageFormat.setOrientation(PageFormat.LANDSCAPE);
        book.append(new Document(), documentPageFormat);

        //--- Tell the printJob to use the book as the pageable object
        printJob.setPageable(book);

        //--- Show the print dialog box. If the user click the
        //--- print button we then proceed to print else we cancel
        //--- the process.
        if (printJob.printDialog()) {
            try {
                printJob.print();
            } catch (Exception PrintException) {
                PrintException.printStackTrace();
            }
        }
    }
    private class IntroPage implements Printable {
        public int print(Graphics g, PageFormat pageFormat, int page) {

            //--- Create the Graphics2D object
            Graphics2D g2d = (Graphics2D) g;

            //--- Translate the origin to 0,0 for the top left corner
            g2d.translate(pageFormat.getImageableX(), pageFormat
                    .getImageableY());

            //--- Set the default drawing color to black
            g2d.setPaint(Color.black);

            //--- Draw a border arround the page
            Rectangle2D.Double border = new Rectangle2D.Double(0, 0, pageFormat
                    .getImageableWidth(), pageFormat.getImageableHeight());
            g2d.draw(border);

            //--- Print the title
            String titleText = "Printing in Java Part 2";
            Font titleFont = new Font("helvetica", Font.BOLD, 36);
            g2d.setFont(titleFont);

            //--- Compute the horizontal center of the page
            FontMetrics fontMetrics = g2d.getFontMetrics();
            double titleX = (pageFormat.getImageableWidth() / 2)
                    - (fontMetrics.stringWidth(titleText) / 2);
            double titleY = 3 * POINTS_PER_INCH;
            g2d.drawString(titleText, (int) titleX, (int) titleY);

            return (PAGE_EXISTS);
        }
    }
    private class Document implements Printable {
        public int print(Graphics g, PageFormat pageFormat, int page) {

            //--- Create the Graphics2D object
            Graphics2D g2d = (Graphics2D) g;

            //--- Translate the origin to 0,0 for the top left corner
            g2d.translate(pageFormat.getImageableX(), pageFormat
                    .getImageableY());

            //--- Set the drawing color to black
            g2d.setPaint(Color.black);

            //--- Draw a border arround the page using a 12 point border
            g2d.setStroke(new BasicStroke(12));
            Rectangle2D.Double border = new Rectangle2D.Double(0, 0, pageFormat
                    .getImageableWidth(), pageFormat.getImageableHeight());

            g2d.draw(border);

            //--- Print the text one inch from the top and laft margins
            g2d.drawString("This the content page", POINTS_PER_INCH,
                    POINTS_PER_INCH);

            //--- Validate the page
            return (PAGE_EXISTS);

        }
    }

} // Example2
