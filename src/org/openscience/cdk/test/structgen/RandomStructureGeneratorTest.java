/* $RCSfile$    
 * $Author$    
 * $Date$    
 * $Revision$
 * 
 * Copyright (C) 1997-2003  The Chemistry Development Kit (CDK) project
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
 * Foundation, Inc., 59 Temple Place - Suite 330, Boston, MA  02111-1307, USA. 
 */
package org.openscience.cdk.test.structgen;

import org.openscience.cdk.controller.*;
import org.openscience.cdk.*;
import org.openscience.cdk.io.*;
import org.openscience.cdk.layout.*;
import org.openscience.cdk.renderer.*;
import org.openscience.cdk.structgen.*;
import org.openscience.cdk.templates.*;
import java.util.*;
import java.io.*;
import java.net.URL;
import javax.vecmath.Vector2d;
import junit.framework.*;

/**
 * @cdkPackage test
 */
public class RandomStructureGeneratorTest extends TestCase
{
	public boolean debug = false;
	boolean standAlone = false;

	public MoleculeListViewer moleculeListViewer = null;
    
    public RandomStructureGeneratorTest(String name) {
        super(name);
    }
    
    public RandomStructureGeneratorTest() {
        this("RandomStructureGeneratorTest");
        moleculeListViewer = new MoleculeListViewer();
    }

	public void setStandAlone(boolean standAlone)
	{
		this.standAlone = standAlone;
	}

	public static Test suite()
	{
		return new TestSuite(RandomStructureGeneratorTest.class);
	}

	/** A complex alkaloid with two separate ring systems to 
	  * be laid out.
	  */
	public void testIt()
	{
		String s = null;
		Vector structures = new Vector();	
		Molecule mol = null;
		Molecule molecule = MoleculeFactory.loadMolecule("data/mdl/a-pinene.mol");
		//System.out.println(molecule);
		RandomGenerator rg = new RandomGenerator();
		rg.setMolecule(molecule);
	
		for (int f = 0; f < 1000; f++)
		{
			if (debug) System.out.println("Proposing structure no. " + f);
			if (debug) System.out.println("Entering rg.proposeStructure()");
			mol = rg.proposeStructure();
			if ((double)f/(double)50 == f/50)
			{
				structures.addElement(mol.clone());
				if (debug)
				{
					s = "BondCounts:    ";
					for (int g = 0; g < mol.getAtomCount(); g++)
					{
						s += mol.getBondCount(mol.getAtomAt(g)) + " ";
					}
					//System.out.println(s);
					s = "BondOrderSums: ";
					for (int g = 0; g < mol.getAtomCount(); g++)
					{
						s += mol.getBondOrderSum(mol.getAtomAt(g)) + " ";
					}
					//System.out.println(s);
					s = "Bonds: ";
                    Bond[] bonds = mol.getBonds();
					for (int g = 0; g < bonds.length; g++)
					{
						s += bonds[g].getOrder() + " ";
					}
					//System.out.println(s);
				}
			}
			rg.acceptStructure();
		}
		everythingOk(structures);
	}


	private boolean everythingOk(Vector structures)
	{
		StructureDiagramGenerator sdg = null;
		MoleculeViewer2D mv = null;
		Molecule mol = null;
		for (int f = 0; f < structures.size(); f+=100)
		{
			sdg = new StructureDiagramGenerator();

			mol = (Molecule)structures.elementAt(f);
			sdg.setMolecule((Molecule)mol.clone());

			try
			{
				sdg.generateCoordinates(new Vector2d(0,1));
			}
			catch(Exception exc)
			{
				exc.printStackTrace();
				fail("*** Exit due to an unexpected error during coordinate generation ***");
			}
            if (standAlone) {
                mv.setAtomContainer(sdg.getMolecule());
                mv = new MoleculeViewer2D();
                //			Renderer2DModel r2dm = new Renderer2DModel();
                //			r2dm.setDrawNumbers(true);
                //			mv.setRenderer2DModel(r2dm);
                moleculeListViewer.addStructure(mv, "RandomGent Result no. " + (f + 1));
            }
		}
		return true;
	}
	
	public static void main(String[] args)
	{
		RandomStructureGeneratorTest test = new RandomStructureGeneratorTest();
		test.setStandAlone(true);
        test.testIt();
	}
}

