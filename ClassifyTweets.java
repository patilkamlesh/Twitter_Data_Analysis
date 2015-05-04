package storm.starter;

import weka.core.*;
import weka.classifiers.meta.FilteredClassifier;

import java.io.*;
import java.util.ArrayList;

public class ClassifyTweets {

        String testInput;
        Instances instances;
        weka.classifiers.trees.J48 cls =new weka.classifiers.trees.J48();
       
        FilteredClassifier classifier;
        public ArrayList<ClassifiedTweet> parentClassify (String modelFileName, String testFileName) {
        	ClassifyTweets c = new ClassifyTweets();
        	ArrayList<ClassifiedTweet> listClassifiedTweets = new ArrayList<ClassifiedTweet>();
            File dir = new File(testFileName);
            File [] listFiles = dir.listFiles();
            System.out.println("number of Files: " + listFiles.length);
            String fileName = "";
            for (int i = 0; i < listFiles.length; i++) {
				if (listFiles[i].isFile()) {
					fileName = listFiles[i].getName();
					System.out.println("fileName: " + fileName);
					try {
						FileInputStream fis = new FileInputStream(testFileName+"/"+fileName);
						System.out.print("Intermediate Printing:" + testFileName+"/"+fileName);
						DataInputStream dis = new DataInputStream(fis);
						BufferedReader br = new BufferedReader(new InputStreamReader(dis));      
						String str = "";
			    		while((str = br.readLine()) != null) {
			    			listClassifiedTweets.add(c.classify(modelFileName, str, fileName));
		    			}
			    		br.close();
						fis.close();
						dis.close();
						
					} catch (FileNotFoundException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					
				}
			}
            
        	
            return listClassifiedTweets;
        }
        
        public ClassifiedTweet classify(String modelFileName, String testInputStr, String fileName) throws Exception {


				testInput = testInputStr;

				ObjectInputStream in = new ObjectInputStream(new FileInputStream(modelFileName));
				Object tmp = in.readObject();
				classifier = (FilteredClassifier) tmp;
//				Classifier cls = (Classifier) weka.core.SerializationHelper.read("C:/StormData/dt.model");
				in.close();

				Attribute testInputAttr = new Attribute("testInput",(FastVector) null);

				FastVector classVals = new FastVector(2);
                classVals.addElement("Negative");
                classVals.addElement("Positive");
                
                Attribute classAttr = new Attribute("class", classVals);

				FastVector attrs = new FastVector(2);
                attrs.addElement(testInputAttr);
                attrs.addElement(classAttr);

				instances = new Instances("Test relation", attrs, 1);           
				instances.setClassIndex(1);
                Instance instance = new Instance(2);
                instance.setValue(testInputAttr, testInput);
                instances.add(instance);

				double pred = classifier.classifyInstance(instances.instance(0));
				System.out.println("Test input: \""+testInputStr+"\" classified as: "+instances.classAttribute().value((int) pred));
				String []splitArray = testInputStr.split("::");
				//int lenTeamFilePath = testInputStr.split("/").length;
				String teamName = fileName.replace(".txt", "");
				System.out.println("teamName: " + teamName);
				ClassifiedTweet cT = new ClassifiedTweet(splitArray[0], splitArray[1], teamName, 
						instances.classAttribute().value((int) pred));
				return cT;
        }
}  
    


