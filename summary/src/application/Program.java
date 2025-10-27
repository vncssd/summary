package application;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import model.entities.Product;

public class Program {

    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);
        System.out.print("Path: ");
        String sourceFileStr = sc.nextLine();

        List<Product> productList = new ArrayList<>();

        File sourceFile = new File(sourceFileStr);
        String sourceFolder = sourceFile.getParent();

        boolean success = new File(sourceFolder + "\\out").mkdir();

        String targetFile = sourceFolder + "\\out\\summary.csv";

        try (BufferedReader br = new BufferedReader(new FileReader(sourceFileStr))){

            String productCsv = br.readLine();

            while (productCsv != null){

                String [] fields = productCsv.split(",");

                String name = fields[0];
                double price = Double.parseDouble(fields[1]);
                int quantity = Integer.parseInt(fields[2]);

                productList.add(new Product(name, price, quantity));

                productCsv = br.readLine();
            }
            try (BufferedWriter bw = new BufferedWriter(new FileWriter(targetFile))){

                for (Product product : productList){
                    bw.write(product.getName() + ", " + String.format("%.2f", product.totalPrice()));
                    bw.newLine();
                }
                if (success) {
                    System.out.print(targetFile + " created successfully! ");
                }
                else {
                    System.out.print(targetFile + "not created");
                }
            }
            catch (IOException exception){
                System.out.print ("ERROR CREATING FILE " + exception.getMessage());
            }

        }
        catch (IOException exception){
            System.out.print("ERROR! " + exception.getMessage());

        }

        sc.close();

    }
}
