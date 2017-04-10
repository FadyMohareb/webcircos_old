/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package uk.ac.cranfield.bix.utilities;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.List;
import uk.ac.cranfield.bix.controllers.rest.GffDataPoint;
import uk.ac.cranfield.bix.controllers.rest.HeatMapDataPoint;
import uk.ac.cranfield.bix.controllers.rest.HistogramDataPoint;
import uk.ac.cranfield.bix.controllers.rest.LineDataPoint;
import uk.ac.cranfield.bix.controllers.rest.finalObjects.Sequence;
import static uk.ac.cranfield.bix.utilities.fileParser.Coverage_Genomic.CoverageData;
import static uk.ac.cranfield.bix.utilities.fileParser.Coverage_Transcriptomic.CoverageDataTranscriptomics;
import static uk.ac.cranfield.bix.utilities.fileParser.Gff3Parser.GffDataPoints;
import static uk.ac.cranfield.bix.utilities.fileParser.VCFParsers.HistogramData;

/**
 *
 * @author solene
 */
public class SerializeDeserialize {

    public static void SerializeSequence(List<Sequence> list, String filepath) {
        try {
            FileOutputStream fileOut = new FileOutputStream(filepath);
            ObjectOutputStream out = new ObjectOutputStream(fileOut);
            out.writeObject(list);
            out.close();
            fileOut.close();
            System.out.println("\nSerialization Successful... Checkout your specified output file..\n");

        } catch (FileNotFoundException e) {
            System.out.println(e.getMessage());
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }

    public static void SerializeGff(List<String[]> list, String filepath) {
        try {
            List<GffDataPoint> GffDataPoints = GffDataPoints(list);
            FileOutputStream fileOut = new FileOutputStream(filepath);
            ObjectOutputStream out = new ObjectOutputStream(fileOut);
            out.writeObject(GffDataPoints);
            out.close();
            fileOut.close();
            System.out.println("\nSerialization Successful... Checkout your specified output file..\n");

        } catch (FileNotFoundException e) {
            System.out.println(e.getMessage());
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }

    public static void SerializeVcf(ArrayList<String[]> list, String filepath) {
        try {
            List<HistogramDataPoint> HistogramData = HistogramData(list);
            FileOutputStream fileOut = new FileOutputStream(filepath);
            ObjectOutputStream out = new ObjectOutputStream(fileOut);
            out.writeObject(HistogramData);
            out.close();
            fileOut.close();
            System.out.println("\nSerialization Successful... Checkout your specified output file..\n");

        } catch (FileNotFoundException e) {
            System.out.println(e.getMessage());
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }

    public static void SerializeVcfCoverageGenomics(ArrayList<Object[]> list, String filepath) {
        try {
            List<LineDataPoint> lineData = CoverageData(list);
            try (FileOutputStream fileOut = new FileOutputStream(filepath); ObjectOutputStream out = new ObjectOutputStream(fileOut)) {
                out.writeObject(lineData);
            }
            System.out.println("\nSerialization Successful... Checkout your specified output file..\n");

        } catch (FileNotFoundException e) {
            System.out.println(e.getMessage());
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }

    public static void SerializeTranscriptomicCov(ArrayList<Object[]> list, String filepath) {
        try {
            List<LineDataPoint> linePoints = CoverageDataTranscriptomics(list);
            try (FileOutputStream fileOut = new FileOutputStream(filepath); ObjectOutputStream out = new ObjectOutputStream(fileOut)) {
                out.writeObject(linePoints);
            }
            System.out.println("\nSerialization Successful... Checkout your specified output file..\n");

        } catch (FileNotFoundException e) {
            System.out.println(e.getMessage());
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }
    
    public static void SerializeExpression(List<HeatMapDataPoint> list, String filepath) {
        try {
            try (FileOutputStream fileOut = new FileOutputStream(filepath); ObjectOutputStream out = new ObjectOutputStream(fileOut)) {
                out.writeObject(list);
            }
            System.out.println("\nSerialization Successful... Checkout your specified output file..\n");

        } catch (FileNotFoundException e) {
            System.out.println(e.getMessage());
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }

    public static Object Deserialize(String filename) throws ClassNotFoundException {
        try {
            FileInputStream fileIn = new FileInputStream(new File(filename));
            ObjectInputStream in = new ObjectInputStream(fileIn);
            Object readObject = in.readObject();
            in.close();
            fileIn.close();
            return readObject;
        } catch (FileNotFoundException e) {
            System.out.println(e.getMessage());
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
        return null;
    }

}
