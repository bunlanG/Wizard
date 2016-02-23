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

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.JButton;
import javax.swing.JDialog;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;

/**
 * .
 *
 * @author bunlang
 */
public class Wizard extends JDialog {
    private JButton _nextBut;
    private JButton _backBut;
    private JButton _finishBut;
    private JButton _cancelBut;

    public Wizard() {
        super();

        Dimension butDim = new Dimension(90,26);
        Dimension litSpace = new Dimension(2,34);
        Dimension bigSpace = new Dimension(12,34);
        Dimension padSpace = new Dimension(4,34);

        _nextBut = new JButton("Next");
        _nextBut.setPreferredSize(butDim);
        _backBut = new JButton("Back");
        _backBut.setPreferredSize(butDim);
        _finishBut = new JButton("Finish");
        _finishBut.setPreferredSize(butDim);
        _cancelBut = new JButton("Cancel");
        _cancelBut.setPreferredSize(butDim);

        Box b = Box.createHorizontalBox();
        b.setBorder(BorderFactory.createLineBorder(Color.GRAY));
        b.add(Box.createGlue());
        b.add(Box.createRigidArea(padSpace));
        b.add(_backBut);
        b.add(Box.createRigidArea(litSpace));
        b.add(_nextBut);
        b.add(Box.createRigidArea(bigSpace));
        b.add(_finishBut);
        b.add(Box.createRigidArea(litSpace));
        b.add(_cancelBut);
        b.add(Box.createRigidArea(padSpace));

        this.add(b, BorderLayout.SOUTH);
    }
}
