package cimmyt.maize.ui.events;

/**
 * 
 * @author George - george.dma@gmail.com
 * Created on: Mar 12, 2015
 *
 */
public enum Events {

        ADD("ADD"),
        DEL("DEL");
        
        private String strEvent = null;
        Events(String strEvent) {
                this.strEvent = strEvent;
        }
        
        public final String getEventString() {
                return strEvent;
        }
}
