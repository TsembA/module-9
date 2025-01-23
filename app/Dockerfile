# Use Node.js base image
FROM node:18

# Set the working directory inside the container
WORKDIR /usr/src/app

# Copy package.json and package-lock.json first
COPY package*.json ./

# Install dependencies
RUN npm install

# Copy the rest of your application into the container
COPY . .

# Expose port 3000
EXPOSE 3000

# Start the Node.js application
CMD ["node", "server.js"]
