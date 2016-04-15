/*
 * yaw4j : Yet Another Wizard for Java
 * Copyright (C) 2016 - Ronan GUILBAULT
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */

package com.bunlang.yaw4j;

import org.junit.Test;

import javax.swing.JLabel;

/**
 * .
 *
 * @author bunlang
 */
public class WizardUITest {
    private final WizardListener wl = new WizardListener() {
        @Override
        public void wizardUpdated(WizardEvent e) {
            if(e.getSource() instanceof Wizard) {
                Wizard wiz = (Wizard) e.getSource();

                if(wiz.isAccepted()) {
                    System.out.println("Finish");
                } else {
                    System.out.println("Cancel");
                }
            }
        }
    };

    @Test
    public void uiTest() {
        Wizard wiz = new Wizard();
        wiz.addWizardListener(wl);

        WizardPage wp1 = new WizardPage();
        wp1.add(new JLabel("First page"));
        wp1.setTitle("First page");
        wp1.setSubtitle("First page.");
        WizardPage wp2 = new WizardPage();
        wp2.add(new JLabel("Another page"));
        wp2.setTitle("Another page");
        wp2.setSubtitle("Another page.");
        WizardPage wp3 = new WizardPage();
        wp3.add(new JLabel("Last page"));
        wp3.setTitle("Last page");
        wp3.setSubtitle("Last page.");

        wiz.addWizardPage(wp1);
        wiz.addWizardPage(wp2);
        wiz.addWizardPage(wp3);

        wiz.setVisible(true);

        while(wiz.isShowing()) {
            try {
                Thread.sleep(1000);
            } catch(InterruptedException ex) {

            }
        }
    }
}
