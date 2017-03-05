package cimmyt.maize.options;

/**
 * 
 * @author George El Haddad (george.dma@gmail.com)
 * <p>
 * Created on: Mar 14, 2015
 *
 */
public class ParticleAnalysisOptions implements AnalysisOptions {

        private AnalysisOption optionKey = null;
        private double minParticleSize = 0.0;
        private double maxParticleSize = 0.0;
        private double minParticleCirc = 0.0;
        private double maxParticleCirc = 0.0;
        private boolean isMaxParticleSizeInfinity = false;
        
        public ParticleAnalysisOptions() {

        }
        
        public final double getMinParticleSize() {
                return minParticleSize;
        }

        public final void setMinParticleSize(double minParticleSize) {
                this.minParticleSize = minParticleSize;
        }

        public final double getMinParticleCirc() {
                return minParticleCirc;
        }

        public final void setMinParticleCirc(double minParticleCirc) {
                this.minParticleCirc = minParticleCirc;
        }
        
        public final double getMaxParticleSize() {
                return maxParticleSize;
        }

        public final void setMaxParticleSize(double maxParticleSize) {
                this.maxParticleSize = maxParticleSize;
        }

        public final double getMaxParticleCirc() {
                return maxParticleCirc;
        }

        public final void setMaxParticleCirc(double maxParticleCirc) {
                this.maxParticleCirc = maxParticleCirc;
        }

        public boolean isMaxParticleSizeInfinity() {
                return isMaxParticleSizeInfinity;
        }

        public void setMaxParticleSizeInfinity(boolean isMaxParticleSizeInfinity) {
                this.isMaxParticleSizeInfinity = isMaxParticleSizeInfinity;
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
