/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package uk.ac.ebi.mdk.tool.inchi;

import com.google.common.io.Files;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;
import net.sf.jniinchi.INCHI_OPTION;
import org.apache.log4j.Logger;
import org.openscience.cdk.interfaces.IAtomContainer;
import org.openscience.cdk.io.MDLV2000Writer;
import uk.ac.ebi.mdk.tool.inchi.InChIMoleculeChecker.InChIMoleculeCheckerResult;

/**
 *
 * @author pmoreno
 */
public class InChIProducerBinary102beta extends InChIProducer {

    private final String path = System.getProperty("user.home") + System.getProperty("file.separator") + ".uk.ac.ebi.metabolomes" + System.getProperty("file.separator") + "bin/";
    //private final String tmp = System.getProperty("user.home") + System.getProperty("file.separator") + ".uk.ac.ebi.metabolomes" + System.getProperty("file.separator") + "tmp/";
    //private final String shellMol2InChi = "mol2InChI.sh";
    private final String shellMol2InChi = "mol2InChI_1.03.sh";
    private String inchiOptions;
    private Logger logger = Logger.getLogger(InChIProducerBinary102beta.class.getName());

    public boolean checkBinaries() {
        File shellScript = new File(path+shellMol2InChi);
        return shellScript.canRead();
    }

    @Override
    public InChIResult calculateInChI(IAtomContainer mol) {
        if(inchiOptions==null)
            inchiOptions="";
        
        InChIMoleculeCheckerResult resCheck = InChIMoleculeChecker.getInstance().checkMolecule(mol);
        if(!resCheck.isInChIFit()) {
            if(resCheck.isGenericAtom())
                logger.trace("Skipping generic molecule");
            else
                logger.trace("Molecule has null bonds, atoms or is emtpy");
            return null;
        }
        // improve this
        
        //File tmpDir = new File(tmp + File.separator + "dir" + Math.round(1000 * Math.random()));
        File tmpDir = Files.createTempDir();
        if (!tmpDir.mkdir()) {
            logger.error("Cannot create temporary directory");
            return null;
        }
        String tmpFileName = tmpDir.getAbsolutePath() + File.separator + "fileInChiFromCDKMol";
        String outputFileName = tmpDir.getAbsolutePath() + File.separator + "output";
        String logFileName = tmpDir.getAbsolutePath() + File.separator + "logFile";
        MDLV2000Writer w;
        try {
            w = new MDLV2000Writer(new FileWriter(new File(tmpFileName)));
            w.writeMolecule(mol);
            w.close();
        } catch (Exception ex) {
            logger.error("Could not write mol file for inchi calculations.", ex);
            return null;
        }
        //System.out.println(path + shellMol2InChi + " " + tmpFileName + " " + outputFileName + " " + logFileName + " " + inchiOptions);
        if (this.runProcess(path + shellMol2InChi + " " + tmpFileName + " " + outputFileName + " " + logFileName +" "+inchiOptions)) {
            String line = null;
            BufferedReader r;
            try {
                r = new BufferedReader(new FileReader(outputFileName));
                line = r.readLine();
                line += "\t"+r.readLine();
                r.close();
            } catch (IOException ex) {
                logger.error("Problems reading binary result file", ex);
                return null;
            }
            String[] tokens = line.split("\t");
            if (tokens.length < 4) {
                return null;
            }

            InChIResult res = new InChIResult();
            res.setInchi(tokens[1]);
            res.setAuxInfo(tokens[2]);
            res.setInchiKey(tokens[3]);
            deleteTmpDirAndFiles(tmpDir);
            return res;
        } else {
            return null;
        }


    }

    private boolean runProcess(String command) {
        Runtime rt = Runtime.getRuntime();
        Process p = null;
        try {
            p = rt.exec(command);
        } catch (IOException ex) {
            logger.error("Could not execute process running inchi binary.", ex);
            return false;
        }
        try {
            p.waitFor();
        } catch (InterruptedException ex) {
            logger.error("Process running inchi binary failed:", ex);
            return false;
        }
        return true;
    }

    private boolean deleteTmpDirAndFiles(File tmpDir) {
        File[] files = tmpDir.listFiles();
        boolean ans = true;
        for (File file : files) {
            ans = ans && file.delete();
        }
        return ans && tmpDir.delete();
    }

    @Override
    public void setInChIOptions(List<INCHI_OPTION> inchiConfigOptions) {
        this.inchiOptions = "";
        for (INCHI_OPTION inchi_option : inchiConfigOptions) {
            if(inchi_option.equals(INCHI_OPTION.SNon)) {
                this.inchiOptions += "-SNon ";
                continue;
            } else if(inchi_option.equals(INCHI_OPTION.DoNotAddH)) {
                this.inchiOptions += "-DoNotAddH ";
                continue;
            } else if(inchi_option.equals(INCHI_OPTION.AuxNone)) {
                this.inchiOptions += "-AuxNone ";
                continue;
            } else if(inchi_option.equals(INCHI_OPTION.RecMet)) {
                this.inchiOptions += "-RecMet ";
                continue;
            } else if(inchi_option.equals(INCHI_OPTION.NoADP)) {
                //don't know how to handle this
                continue;
            } else if(inchi_option.equals(INCHI_OPTION.FixedH)) {
                this.inchiOptions += "-FixedH ";
                continue;
            }

        }
    }
}
