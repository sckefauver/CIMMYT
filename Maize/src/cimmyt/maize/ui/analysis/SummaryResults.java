package cimmyt.maize.ui.analysis;

/**
 * 
 * @author George - george.dma@gmail.com
 * Created on: Mar 27, 2015
 *
 */
public class SummaryResults {

        private String imageName = null;
        private String[] summaryHeadings = null;
        private String[] summaryLine = null;

        public SummaryResults() {

        }
        
        public final void appendResults(String results, String resultsHeading) {
                String[] xtraResults = results.split("\t");
                String[] xtraHeadings = resultsHeading.split("\t");
                
                String[] tmp1 = new String[summaryLine.length+xtraResults.length];
                String[] tmp2 = new String[summaryHeadings.length+xtraHeadings.length];
                
                int j = 0;
                for(int i=0; i < summaryLine.length; i++) {
                        tmp1[j] = summaryLine[i];
                        j++;
                }
                
                for(int i=0; i < xtraResults.length; i++) {
                        tmp1[j] = xtraResults[i];
                        j++;
                }
                
                //--- headings
                
                j = 0;
                for(int i=0; i < summaryHeadings.length; i++) {
                        tmp2[j] = summaryHeadings[i];
                        j++;
                }
                
                for(int i=0; i < xtraHeadings.length; i++) {
                        tmp2[j] = xtraHeadings[i];
                        j++;
                }
                
                summaryLine = tmp1;
                summaryHeadings = tmp2;
                
                xtraResults = null;
                xtraHeadings = null;
        }
        
        
        public final void setImageName(String imageName) {
                this.imageName = imageName;
        }

        public final void setSummaryLine(String summaryLine) {
                if (summaryLine != null) {
                        this.summaryLine = summaryLine.split("\t");
                }
                else {
                        this.summaryLine = null;
                }
        }

        public final void setSummaryLine(String[] summaryLine) {
                this.summaryLine = summaryLine;
        }

        public final void setSummaryHeadings(String summaryHeadings) {
                if (summaryHeadings != null) {
                        this.summaryHeadings = summaryHeadings.split("\t");
                }
                else {
                        this.summaryHeadings = null;
                }
        }

        public final void setSummaryHeadings(String[] summaryHeadings) {
                this.summaryHeadings = summaryHeadings;
        }

        public final String getImageName() {
                return imageName;
        }

        public final String[] getSummaryLine() {
                return summaryLine;
        }

        public final String[] getSummaryHeadings() {
                return summaryHeadings;
        }

        public final boolean hasSummary() {
                return summaryLine != null && summaryLine.length > 0;
        }

        public final String getSummaryCell(int column) {
                return summaryLine[column];
        }

        public final double getSummaryCellDouble(int column) {
                if (SummaryColumns.SCLICE == column) {
                        // SCLICE is a text value so we just return 0.0
                        return 0.0;
                }
                else {
                        return Double.parseDouble(summaryLine[column]);
                }
        }
}
