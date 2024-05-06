package com.example.demo;

import software.amazon.awssdk.auth.credentials.AwsCredentialsProvider;
import software.amazon.awssdk.auth.credentials.DefaultCredentialsProvider;
import software.amazon.awssdk.auth.credentials.ProfileCredentialsProvider;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.ec2.Ec2Client;
import software.amazon.awssdk.services.iam.IamClient;
import software.amazon.awssdk.services.s3.S3Client;

import java.net.HttpURLConnection;
import java.net.URL;


import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class DemoApplication {

	public static void main(String[] args) {
		// Check if running on an AWS EC2 instance
		AwsCredentialsProvider credentialsProvider = checkIfRunningOnEC2()
				? DefaultCredentialsProvider.create() // Use EC2 Instance Profile
				: ProfileCredentialsProvider.create("Dev"); // Use local profile

		// Create an S3 client configured for the US East 1 region
		S3Client s3 = S3Client.builder()
				.credentialsProvider(credentialsProvider)
				.region(Region.US_EAST_1) // Substitua pela região correspondente
				.build();

		// List all S3 buckets and print their names
		System.out.println("__________________________________________");
		System.out.println("Check S3 Bucket names");
		s3.listBuckets().buckets().stream()
				.forEach(bucket -> System.out.println(bucket.name()));

		// Create an IAM client (IAM is a global service)
		IamClient iam = IamClient.builder()
				.credentialsProvider(credentialsProvider)
				.region(Region.AWS_GLOBAL) // IAM é um serviço global
				.build();

		// List all IAM users and print their usernames
		System.out.println("__________________________________________");
		System.out.println("Check IAM Users");
		iam.listUsers().users().stream()
				.forEach(user -> System.out.println(user.userName()));

		// List all EC2 instance IDs in the specified region
		Ec2Client ec2 = Ec2Client.builder()
				.credentialsProvider(credentialsProvider)
				.region(Region.US_EAST_1)
				.build();

		// Exemplo de operação: listar nomes das instâncias EC2
		System.out.println("__________________________________________");
		System.out.println("EC2 Instance Names in the Region:");
		ec2.describeInstances().reservations().stream()
				.flatMap(reservation -> reservation.instances().stream())
				.forEach(instance -> System.out.println(instance.instanceId()));


		// Fechar os clientes
		s3.close();
		iam.close();
		ec2.close();

	}


	private static boolean checkIfRunningOnEC2() {
		try {
			// Connect to the EC2 metadata service
			URL url = new URL("http://169.254.169.254/latest/meta-data/");
			HttpURLConnection connection = (HttpURLConnection) url.openConnection();
			connection.setRequestMethod("GET");
			connection.setConnectTimeout(1000); // Timeout for connecting to the metadata service
			connection.connect();
			return connection.getResponseCode() == 200; // Status OK means we are on EC2
		} catch (Exception e) {
			return false; // Any exception means we are not on EC2
		}

	}
}