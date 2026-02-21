# Restrudent Frontend

Next.js frontend for the Restaurant Review application.

## Getting Started

### Install Dependencies

```bash
npm install
```

### Configure Environment Variables

Create a `.env.local` file in the frontend directory:

```env
NEXT_PUBLIC_API_BASE_URL=http://localhost:8080
NEXT_PUBLIC_KEYCLOAK_URL=http://localhost:9090
NEXT_PUBLIC_KEYCLOAK_REALM=retrurant-review
NEXT_PUBLIC_KEYCLOAK_CLIENT_ID=your-client-id
```

### Run Development Server

```bash
npm run dev
```

Open [http://localhost:3000](http://localhost:3000) with your browser to see the result.

### Build for Production

```bash
npm run build
npm start
```

## Features

- Restaurant listing with pagination
- Restaurant details view
- Search functionality
- Responsive design with Tailwind CSS
- TypeScript for type safety
- Axios for API communication

## Project Structure

```
src/
├── app/            Next.js app directory
│   ├── layout.tsx  Root layout
│   ├── page.tsx    Home page
│   └── globals.css Global styles
├── components/     Reusable React components
├── services/       API service layer
│   └── api.ts      API client and services
├── types/          TypeScript type definitions
│   └── index.ts    Shared types
└── utils/          Utility functions
```

## Technologies

- Next.js 14
- React 18
- TypeScript
- Tailwind CSS
- Axios
- Keycloak JS (for authentication)

## Learn More

To learn more about Next.js, take a look at the following resources:

- [Next.js Documentation](https://nextjs.org/docs)
- [Learn Next.js](https://nextjs.org/learn)
- [Tailwind CSS Documentation](https://tailwindcss.com/docs)
