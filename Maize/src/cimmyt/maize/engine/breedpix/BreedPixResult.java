package cimmyt.maize.engine.breedpix;

/**
 * 
 * @author George El Haddad (george.dma@gmail.com)
 * <p>
 * Created on: Aug 13, 2015
 *
 */
public class BreedPixResult {
        
        private float ihs_i = -1;       // Intensity
        private float ihs_h = -1;       // Hue
        private float ihs_s = -1;       // Saturation
        private float lab_l = -1;       // Lightness
        private float lab_a = -1;       // a*
        private float lab_b = -1;       // b*
        private float luv_u = -1;       // u*
        private float luv_v = -1;       // v*
        private double ga = -1;         // Green Area
        private double gga = -1;        // Greener Green Area
        private double csi = -1;        // CSI calculation (Zaman‑Allah et al. Plant Methods (2015) 11:35)
        private PixelMask ga_roi = null;
        private PixelMask gga_roi = null;
        
        public BreedPixResult() {
                
        }

        public final float getIhs_i() {
                return ihs_i;
        }

        public final void setIhs_i(float ihs_i) {
                this.ihs_i = ihs_i;
        }

        public final float getIhs_h() {
                return ihs_h;
        }

        public final void setIhs_h(float ihs_h) {
                this.ihs_h = ihs_h;
        }

        public final float getIhs_s() {
                return ihs_s;
        }

        public final void setIhs_s(float ihs_s) {
                this.ihs_s = ihs_s;
        }

        public final float getLab_l() {
                return lab_l;
        }

        public final void setLab_l(float lab_l) {
                this.lab_l = lab_l;
        }

        public final float getLab_a() {
                return lab_a;
        }

        public final void setLab_a(float lab_a) {
                this.lab_a = lab_a;
        }

        public final float getLab_b() {
                return lab_b;
        }

        public final void setLab_b(float lab_b) {
                this.lab_b = lab_b;
        }

        public final float getLuv_u() {
                return luv_u;
        }

        public final void setLuv_u(float luv_u) {
                this.luv_u = luv_u;
        }

        public final float getLuv_v() {
                return luv_v;
        }

        public final void setLuv_v(float luv_v) {
                this.luv_v = luv_v;
        }

        public final double getGa() {
                return ga;
        }

        public final void setGa(double ga) {
                this.ga = ga;
        }

        public final double getGga() {
                return gga;
        }

        public final void setGga(double gga) {
                this.gga = gga;
        }
        
        public final double getCsi() {
                return csi;
        }
        
        public final void setCsi(double csi) {
                this.csi = csi;
        }

        public final PixelMask getGa_roi() {
                return ga_roi;
        }

        public final void setGa_roi(PixelMask ga_roi) {
                this.ga_roi = ga_roi;
        }

        public final PixelMask getGga_roi() {
                return gga_roi;
        }

        public final void setGga_roi(PixelMask gga_roi) {
                this.gga_roi = gga_roi;
        }
        
        @Override
        public String toString() {
                StringBuilder sb = new StringBuilder();
                sb.append(this.getClass().getName())
                .append('[')
                .append("HSI=").append(getIhs_h()).append(',').append(getIhs_s()).append(',').append(getIhs_i()).append(';')
                .append("LAB=").append(getLab_l()).append(',').append(getLab_a()).append(',').append(getLab_b()).append(';')
                .append("LUV=").append(getLuv_u()).append(',').append(getLuv_v()).append(';')
                .append("GA=").append(getGa())
                .append(';')
                .append("GGA=").append(getGga())
                .append(';')
                .append("CSI=").append(getCsi())
                .append(';')
                .append(']');
                return sb.toString();
        }
        
        public String toPrettyString() {
                StringBuilder sb = new StringBuilder();
                sb.append("Hue = ").append(getIhs_h()).append('\n')
                .append("Saturation = ").append(getIhs_s()).append('\n')
                .append("Intensity = ").append(getIhs_i()).append('\n')
                .append("Lightness = ").append(getLab_l()).append('\n')
                .append("a* = ").append(getLab_a()).append('\n')
                .append("b* = ").append(getLab_b()).append('\n')
                .append("u* = ").append(getLuv_u()).append('\n')
                .append("v* = ").append(getLuv_v()).append('\n')
                .append("GA = ").append(getGa()).append('\n')
                .append("GGA = ").append(getGga()).append('\n')
                .append("CSI = ").append(getCsi()).append('\n');
                return sb.toString();
        }
}
