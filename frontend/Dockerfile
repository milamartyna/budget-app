# Step 1: build the app
FROM node:20-alpine AS build

WORKDIR /app
COPY package*.json ./
RUN npm install

COPY . .
RUN npm run build


# Step 2: serve with a lightweight web server (e.g. nginx)
FROM nginx:alpine

# Copy built React app into nginx html folder
COPY --from=build /app/build /usr/share/nginx/html

# Optional: replace default nginx config if needed
# COPY nginx.conf /etc/nginx/conf.d/default.conf

EXPOSE 80

CMD ["nginx", "-g", "daemon off;"]
