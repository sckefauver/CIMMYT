package cimmyt.maize.ui;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;
import javax.swing.Box;
import javax.swing.JFrame;
import javax.swing.JPanel;
import cimmyt.maize.ui.events.Events;

/**
 * 
 * @author George - george.dma@gmail.com
 * Created on: Mar 12, 2015
 *
 */
public class ParticleAnalyzerPanel extends JPanel {

        private static final long serialVersionUID = 8200978340403684783L;
        
        private List<ParticleAnalyzerOptionsPanel> optionsList = new ArrayList<ParticleAnalyzerOptionsPanel>(5);
        private Box boxPanel = null;
        
        public ParticleAnalyzerPanel() {
                boxPanel = Box.createVerticalBox();
                setLayout(new BorderLayout(5, 5));
                add(boxPanel, BorderLayout.NORTH);
                addParticleAnalysisPhase();
        }
        
        private final void addParticleAnalysisPhase() {
                ParticleAnalyzerOptionsPanel optionsPanel = new ParticleAnalyzerOptionsPanel();
                optionsPanel.addActionListener(new ActionListener() {
                        @Override
                        public void actionPerformed(ActionEvent e) {
                                particleAnalysisOptions_actionPerformed(e);
                        }
                });
                
                optionsList.add(optionsPanel);
                
                if(optionsList.size() == 1) {
                        optionsPanel.setDelButtonEnable(false);
                }
                else {
                        optionsList.get(optionsList.size()-2).setAddButtonEnabled(false);
                }
                
                boxPanel.add(optionsPanel);
                revalidate();
        }
        
        private final void removeParticleAnalysisPhase(ParticleAnalyzerOptionsPanel optionsPanel) throws NullPointerException {
                if(optionsPanel == null) {
                        throw new NullPointerException("Cannot remove a null optionsPanel from the particle analysis list.");
                }
                
                optionsList.remove(optionsPanel);
                
                if(optionsList.size() == 1) {
                        optionsList.get(0).setAddButtonEnabled(true);
                }
                else {
                        optionsList.get(optionsList.size()-1).setAddButtonEnabled(true); 
                }
                
                boxPanel.remove(optionsPanel);
                revalidate();
                optionsPanel = null;
        }
        
        private final void particleAnalysisOptions_actionPerformed(ActionEvent e) {
                String cmd = e.getActionCommand();
                if(Events.ADD.getEventString().equals(cmd)) {
                        addParticleAnalysisPhase();
                }
                else if(Events.DEL.getEventString().equals(cmd)) {
                        removeParticleAnalysisPhase((ParticleAnalyzerOptionsPanel)e.getSource());
                }
                else {
                        System.err.println("Unknown event command: "+cmd);
                }
        }
        
        public static final void main(String ... args) {
                JFrame frame = new JFrame();
                frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                frame.setLayout(new FlowLayout(FlowLayout.LEFT, 5, 5));
                frame.add(new ParticleAnalyzerPanel());
                frame.setSize(800, 600);
                frame.setVisible(true);
        }
}
