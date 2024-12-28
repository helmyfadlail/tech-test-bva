# step 1
# Set the base image to use the official Node.js 14.x image
FROM node:21-alpine as builder

# Create a new directory in the container to hold the app
WORKDIR /app

# Copy package.json and package-lock.json into the container
COPY package*.json ./

# Install the app's dependencies
RUN npm install

# Copy the rest of the app into the container
COPY . .

# Start the app using Node.js
CMD ["npm", "run", "build"]


# step 2
# Set the base image to use the official nginx
FROM nginx

# expose public port
EXPOSE 3000

# copy default.conf to nginx service
COPY default.conf /etc/nginx/conf.d/default.conf

# copy build app from react vite to nginx
COPY --from=builder /app/dist /usr/share/nginx/html