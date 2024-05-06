# AWS EC2 Java Application Example

## Project Overview

This project is a Java application that interacts with AWS services such as S3, IAM, and EC2. It is designed to demonstrate how to configure and run a Java application that accesses AWS resources conditionally based on whether it's running on an AWS EC2 instance or a local development environment.

## Requirements

- **AWS Account:** You need to have an AWS account.
- **EC2 Instance:** This project should be deployed on an Amazon Linux EC2 instance.
- **IAM Policy:** The EC2 instance must have an IAM role with policies granting access to S3, IAM, and EC2 services.
- **Instance Metadata:** The EC2 instance must have access to the EC2 instance metadata.

## Setup Instructions on Amazon Linux EC2

### 1. Set up the EC2 Instance

Ensure that your Amazon Linux EC2 instance has an IAM role attached with sufficient permissions to access S3, IAM, and EC2 services. Make sure the instance metadata is accessible.

### 2. Install Java and Maven

Log in to your EC2 instance (e.g. SSH or SSM) and install Git, Java and Maven, which are required to build and run the Java application.
Check [correto documentation](https://docs.aws.amazon.com/corretto/latest/corretto-21-ug/amazon-linux-install.html)

```bash
sudo yum update -y
sudo yum install git -y
sudo yum install java-21-amazon-corretto-headless -y
sudo yum install maven -y
```

### 3. Clone the Repository

Clone the project repository into a known directory.

```bash
mkdir -p ~/project
cd ~/project
git clone https://github.com/biagolini/JavaAwsConditionalAuthentication.git
cd JavaAwsConditionalAuthentication
```

### 4. Build the Project

Once the repository is cloned, navigate to the project directory and build the project using Maven.

```bash
mvn clean install
```

### 5. Run the Application

After the build completes, you can run the application.

```bash
java -jar target/demo-0.0.1-SNAPSHOT.jar
```

## Contributing

Feel free to submit issues, create pull requests, or fork the repository to help improve the project.

## License and Disclaimer

This project is open-source and available under the MIT License. You are free to copy, modify, and use the project as you wish. However, any responsibility for the use of the code is solely yours. Please use it at your own risk and discretion.
