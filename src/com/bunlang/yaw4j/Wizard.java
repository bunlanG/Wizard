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
import javax.swing.JLabel;
import javax.swing.JPanel;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Set;
import java.util.Stack;

/**
 * .
 *
 * @author bunlang
 */
public class Wizard extends JDialog {
    // Ui
    private JButton _nextBut;
    private JButton _backBut;
    private JButton _finishBut;
    private JButton _cancelBut;
    private JPanel _content;
    private JLabel _pageTitle;
    private JLabel _pageSubtitle;

    // Observer pattern
    private ArrayList<WizardListener> _observers;

    // WizardPage manager
    private HashMap<Integer, WizardPage> _pages;
    private Stack<Integer> _historyPageId;
    private int _currIndexPage;
    private int _maxIndexPage;
    private int _startIndexPage;
    private final ActionListener _pageManager = new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent actionEvent) {
            if(actionEvent.getSource() instanceof JButton) {
                JButton src = (JButton)actionEvent.getSource();

                if(src == _nextBut) {
                    next();
                }
                if(src == _backBut) {
                    back();
                }
                if(src == _cancelBut) {
                    cancel();
                }
                if(src == _finishBut) {
                    finish();
                }
            }
        }
    };

    // Misc
    private boolean _accepted;

    public Wizard() {
        super();

        Dimension butDim = new Dimension(90,26);
        Dimension litSpace = new Dimension(2,34);
        Dimension bigSpace = new Dimension(12,34);
        Dimension padSpace = new Dimension(4,34);
        Font f = new Font("system", Font.PLAIN, 12);

        _pages = new HashMap<>();
        _historyPageId = new Stack<>();
        _observers = new ArrayList<>();
        _content = new JPanel();
        _maxIndexPage = -1;
        _currIndexPage = 0;
        _startIndexPage = 0;
        _accepted = false;

        _nextBut = new JButton("Next");
        _nextBut.setPreferredSize(butDim);
        _nextBut.addActionListener(_pageManager);
        _backBut = new JButton("Back");
        _backBut.setPreferredSize(butDim);
        _backBut.addActionListener(_pageManager);
        _finishBut = new JButton("Finish");
        _finishBut.setPreferredSize(butDim);
        _finishBut.addActionListener(_pageManager);
        _cancelBut = new JButton("Cancel");
        _cancelBut.setPreferredSize(butDim);
        _cancelBut.addActionListener(_pageManager);

        _pageTitle = new JLabel("Title");
        _pageTitle.setFont(new Font(f.getFontName(), Font.BOLD, f.getSize()));
        _pageSubtitle = new JLabel("Sub-title.");
        _pageSubtitle.setFont(new Font(f.getFontName(), Font.PLAIN, f.getSize()));

        Box bSouth = Box.createHorizontalBox();
        bSouth.setBorder(BorderFactory.createLineBorder(Color.GRAY));
        bSouth.add(Box.createGlue());
        bSouth.add(Box.createRigidArea(padSpace));
        bSouth.add(_backBut);
        bSouth.add(Box.createRigidArea(litSpace));
        bSouth.add(_nextBut);
        bSouth.add(Box.createRigidArea(bigSpace));
        bSouth.add(_finishBut);
        bSouth.add(Box.createRigidArea(litSpace));
        bSouth.add(_cancelBut);
        bSouth.add(Box.createRigidArea(padSpace));

        Box bNorth = Box.createVerticalBox();
        bNorth.add(_pageTitle);
        bNorth.add(_pageSubtitle);

        this.add(bSouth, BorderLayout.SOUTH);
        this.add(bNorth, BorderLayout.NORTH);
        this.add(_content);
    }

    @Override
    public void setVisible(boolean visible) {
        if(visible) {
            // Needs some verifications before showing the wizard
            updateCurrPageId(_startIndexPage);
            System.out.println("CurrIndex : " + _currIndexPage + "/" + _maxIndexPage);
        }

        super.setVisible(visible);
    }

    public void addWizardPage(WizardPage page) {
        addWizardPage(_maxIndexPage + 1,page);
    }

    public void addWizardPage(int index, WizardPage page) {
        _pages.put(index, page);

        // Prepare the next add
        if(index > _maxIndexPage) {
            _maxIndexPage = index;
        }
    }

    protected int nextPageId(int currPageId) {
        if(currPageId < _maxIndexPage) {
            return (currPageId + 1);
        } else {
            return -1;
        }
    }

    public void next() {
        int nextPageId = nextPageId(_currIndexPage);

        if(nextPageId >= 0) {
            _historyPageId.push(_currIndexPage);
            updateCurrPageId(nextPageId);
            System.out.println("currIndexPage : " + _currIndexPage + "/" + _maxIndexPage);
        }
    }

    public void back() {
        if(!_historyPageId.empty()) {
            int currIndexId = _historyPageId.pop();
            updateCurrPageId(currIndexId);
            System.out.println("currIndexPage : " + _currIndexPage + "/" + _maxIndexPage);
        }
    }

    public void cancel() {
        _accepted = false;
        this.setVisible(false);
        updateListeners();
    }

    public void finish() {
        _accepted = true;
        this.setVisible(false);
        updateListeners();
    }

    public boolean isAccepted() {
        return _accepted;
    }

    public void addWizardListener(WizardListener obs) {
        _observers.add(obs);
    }

    public void removeWizardListener(WizardListener obs) {
        _observers.remove(obs);
    }

    public void removeWizardListeners() {
        _observers.clear();
    }

    public void setStartId(int startId) {
        _startIndexPage = startId;
    }

    public int getCurrentId() {
        return _currIndexPage;
    }

    public WizardPage getCurrentPage() {
        return _pages.get(_currIndexPage);
    }

    public Set<Integer> getPageIds() {
        return _pages.keySet();
    }

    protected void updateListeners() {
        WizardEvent e = new WizardEvent(this);
        for(WizardListener obs : _observers) {
            obs.wizardUpdated(e);
        }
    }

    protected boolean isFirstPage() {
        return (_historyPageId.empty());
    }

    protected boolean isLastPage() {
        return (nextPageId(_currIndexPage) == -1);
    }

    private void updateNavButtons() {
        boolean lastPage = isLastPage();
        boolean firstPage = isFirstPage();

        _finishBut.setEnabled(lastPage);
        _nextBut.setEnabled(!lastPage);
        _backBut.setEnabled(!firstPage);
    }

    private void updatePage() {
        String title = _pages.get(_currIndexPage).getTitle();
        String subtitle = _pages.get(_currIndexPage).getSubtitle();

        _content.add(_pages.get(_currIndexPage));
        _pageTitle.setText(title);
        _pageSubtitle.setText(subtitle);

        this.pack();
    }

    private void updateCurrPageId(int newPageId) {
        _pages.get(_currIndexPage).setVisible(false);
        _currIndexPage = newPageId;
        _pages.get(_currIndexPage).setVisible(true);

        updatePage();
        updateNavButtons();
    }
}
