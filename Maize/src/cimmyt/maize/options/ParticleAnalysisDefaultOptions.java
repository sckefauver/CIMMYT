package cimmyt.maize.options;

/**
 * 
 * @author George - george.dma@gmail.com
 * <br>
 * Created on: Mar 27, 2015
 *
 */
public class ParticleAnalysisDefaultOptions implements AnalysisOptions {

        private AnalysisOption optionKey = null;
        private boolean saveOverlays = false;
        private boolean saveSummaries = false;
        private String saveOverlaysDir = null;
        private String saveSummaryFile = null;
        private boolean measureRgb = false;
        
        public ParticleAnalysisDefaultOptions() {
                
        }

        public final boolean isSaveOverlays() {
                return saveOverlays;
        }

        public final void setSaveOverlays(boolean saveOverlays) {
                this.saveOverlays = saveOverlays;
        }

        public final boolean isSaveSummaries() {
                return saveSummaries;
        }

        public final void setSaveSummaries(boolean saveSummaries) {
                this.saveSummaries = saveSummaries;
        }

        public final String getSaveOverlaysDir() {
                return saveOverlaysDir;
        }

        public final void setSaveOverlaysDir(String saveOverlaysDir) {
                this.saveOverlaysDir = saveOverlaysDir;
        }

        public final String getSaveSummaryFile() {
                return saveSummaryFile;
        }

        public final void setSaveSummaryFile(String saveSummaryFile) {
                this.saveSummaryFile = saveSummaryFile;
        }
        
        public final boolean isMeasureRgb() {
                return measureRgb;
        }

        public final void setMeasureRgb(boolean measureRgb) {
                this.measureRgb = measureRgb;
        }

        @Override
        public void setOptionKey(AnalysisOption optionKey) {
                this.optionKey = optionKey;
        }

        @Override
        public AnalysisOption getOptionKey() {
                return optionKey;
        }
}
