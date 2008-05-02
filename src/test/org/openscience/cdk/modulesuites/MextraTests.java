/* $Revision$ $Author$ $Date$
 *
 * Copyright (C) 1997-2007  The Chemistry Development Kit (CDK) project
 *
 * Contact: cdk-devel@lists.sourceforge.net
 *
 * This program is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Lesser General Public License
 * as published by the Free Software Foundation; either version 2.1
 * of the License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with this program; if not, write to the Free Software
 * Foundation, Inc., 51 Franklin St, Fifth Floor, Boston, MA 02110-1301 USA.
 */
package org.openscience.cdk.modulesuites;

import junit.framework.JUnit4TestAdapter;
import junit.framework.Test;
import junit.framework.TestSuite;

import org.openscience.cdk.CloneAtomContainerTest;
import org.openscience.cdk.VariousTests;
import org.openscience.cdk.applications.APIVersionTesterTest;
import org.openscience.cdk.applications.swing.MoleculeListViewer;
import org.openscience.cdk.applications.undoredo.AddAtomsAndBondsEditTest;
import org.openscience.cdk.applications.undoredo.AddHydrogenEditTest;
import org.openscience.cdk.applications.undoredo.AdjustBondOrdersEditTest;
import org.openscience.cdk.applications.undoredo.ChangeAtomSymbolEditTest;
import org.openscience.cdk.applications.undoredo.ChangeChargeEditTest;
import org.openscience.cdk.applications.undoredo.ChangeIsotopeEditTest;
import org.openscience.cdk.applications.undoredo.CleanUpEditTest;
import org.openscience.cdk.applications.undoredo.ConvertToPseudoAtomEditTest;
import org.openscience.cdk.applications.undoredo.ConvertToRadicalEditTest;
import org.openscience.cdk.applications.undoredo.FlipEditTest;
import org.openscience.cdk.applications.undoredo.RemoveAtomsAndBondsEditTest;
import org.openscience.cdk.atomtype.HybridizationMatcherTest;
import org.openscience.cdk.atomtype.HybridizationStateATMatcherTest;
import org.openscience.cdk.atomtype.ValencyMatcherTest;
import org.openscience.cdk.fingerprint.ExtendedFingerprinterTest;
import org.openscience.cdk.fingerprint.StandardSubstructureSetsTest;
import org.openscience.cdk.fingerprint.SubstructureFingerprinterTest;
import org.openscience.cdk.geometry.RDFCalculatorTest;
import org.openscience.cdk.geometry.alignment.KabschAlignmentTest;
import org.openscience.cdk.index.CASNumberTest;
import org.openscience.cdk.io.ShelXWriterTest;
import org.openscience.cdk.layout.HydrogenPlacerTest;
import org.openscience.cdk.layout.OverlapResolverTest;
import org.openscience.cdk.layout.StructureDiagramGeneratorTest;
import org.openscience.cdk.layout.TemplateHandlerTest;
import org.openscience.cdk.similarity.DistanceMomentTest;
import org.openscience.cdk.similarity.TanimotoTest;
import org.openscience.cdk.tools.DeAromatizationToolTest;
import org.openscience.cdk.tools.HOSECodeAnalyserTest;
import org.openscience.cdk.tools.HOSECodeGeneratorTest;

/**
 * TestSuite that runs all the sample tests.
 *
 * @cdk.module  test-extra
 * @cdk.depends log4j.jar
 * @cdk.depends junit.jar
 */
public class MextraTests {
    
    static MoleculeListViewer moleculeListViewer = null;
    
    public static Test suite( )
    {
        TestSuite suite= new TestSuite("All CDK Tests");

        // Individual Tests
        suite.addTest(new JUnit4TestAdapter(CloneAtomContainerTest.class));
        // from cdk.test.applications
        suite.addTest(new JUnit4TestAdapter(APIVersionTesterTest.class));
        // from cdk.test.atomtype (unstable atom type stuff)
        suite.addTest(new JUnit4TestAdapter(ValencyMatcherTest.class));
        suite.addTest(new JUnit4TestAdapter(HybridizationStateATMatcherTest.class));
        suite.addTest(new JUnit4TestAdapter(HybridizationMatcherTest.class));
        // from cdk.test.geometry
        suite.addTest(RDFCalculatorTest.suite());
        // from cdk.test.geometry.align
        suite.addTest(new JUnit4TestAdapter(KabschAlignmentTest.class));
        // from cdk.test.index
        suite.addTest(CASNumberTest.suite());
        // from cdk.test.isomorphism
        // from cdk.test.layout
        suite.addTest(new JUnit4TestAdapter(StructureDiagramGeneratorTest.class));
        suite.addTest(HydrogenPlacerTest.suite());
        suite.addTest(OverlapResolverTest.suite());
        suite.addTest(TemplateHandlerTest.suite());
        // from cdk.test.math
        // from cdk.test.similarity
        suite.addTest(TanimotoTest.suite());
        suite.addTest(DistanceMomentTest.suite());
        suite.addTest(HOSECodeGeneratorTest.suite());
        suite.addTest(HOSECodeAnalyserTest.suite());
        suite.addTest(new JUnit4TestAdapter(ExtendedFingerprinterTest.class));
        suite.addTest(new JUnit4TestAdapter(SubstructureFingerprinterTest.class));
        suite.addTest(new JUnit4TestAdapter(StandardSubstructureSetsTest.class));
        suite.addTest(new JUnit4TestAdapter(DeAromatizationToolTest.class));
        suite.addTest(ShelXWriterTest.suite());
        
        // Below are the tests that are not always possible to execute, because
        // the class might not be compiled (depeding on Ant and Java VM versions).

        // from cdk.test.iupac
        try {
            Class testClass = ClassLoader.getSystemClassLoader().loadClass("org.openscience.cdk.iupac.ParserTest");
            suite.addTest(new TestSuite(testClass));
            System.out.println("Found IUPAC Parser test.");
        } catch (Exception exception) {
            // ok, do without. Probably compiled with Ant < 1.6
            System.out.println("Could not load the IUPAC Parser test: " + exception.getMessage());
        }

        //from cdk.test.applications.jchempaint.undoredo
        suite.addTest(ConvertToPseudoAtomEditTest.suite());
        suite.addTest(ConvertToRadicalEditTest.suite());
        suite.addTest(ChangeIsotopeEditTest.suite());
        suite.addTest(ChangeAtomSymbolEditTest.suite());
        suite.addTest(CleanUpEditTest.suite());
        suite.addTest(AddHydrogenEditTest.suite());
        suite.addTest(AdjustBondOrdersEditTest.suite());
        suite.addTest(FlipEditTest.suite());
        suite.addTest(AddAtomsAndBondsEditTest.suite());
        suite.addTest(RemoveAtomsAndBondsEditTest.suite());
        suite.addTest(ChangeChargeEditTest.suite());
        
        // other
        suite.addTest(VariousTests.suite());        
        
        return suite;
    }
    
}