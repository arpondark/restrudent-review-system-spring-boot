import axios from 'axios';
import { Restaurant, CreateRestaurantRequest, Page } from '@/types';

const API_BASE_URL = process.env.NEXT_PUBLIC_API_BASE_URL || 'http://localhost:8080';

const apiClient = axios.create({
  baseURL: API_BASE_URL,
  headers: {
    'Content-Type': 'application/json',
  },
});

// Add auth token to requests
if (typeof window !== 'undefined') {
  apiClient.interceptors.request.use((config) => {
    const token = localStorage.getItem('token');
    if (token) {
      config.headers.Authorization = `Bearer ${token}`;
    }
    return config;
  });
}

export const restaurantService = {
  async getAll(page = 0, size = 20): Promise<Page<Restaurant>> {
    const response = await apiClient.get(`/api/restaurants?page=${page}&size=${size}`);
    return response.data;
  },

  async getById(id: string): Promise<Restaurant> {
    const response = await apiClient.get(`/api/restaurants/${id}`);
    return response.data;
  },

  async create(data: CreateRestaurantRequest): Promise<Restaurant> {
    const response = await apiClient.post('/api/restaurants', data);
    return response.data;
  },

  async update(id: string, data: CreateRestaurantRequest): Promise<Restaurant> {
    const response = await apiClient.put(`/api/restaurants/${id}`, data);
    return response.data;
  },

  async delete(id: string): Promise<void> {
    await apiClient.delete(`/api/restaurants/${id}`);
  },

  async search(query: string, page = 0, size = 20): Promise<Page<Restaurant>> {
    const response = await apiClient.get(`/api/restaurants/search?query=${query}&page=${page}&size=${size}`);
    return response.data;
  },
};

export const photoService = {
  async upload(file: File): Promise<{ url: string; uploadDate: string }> {
    const formData = new FormData();
    formData.append('file', file);

    const response = await apiClient.post('/api/photos', formData, {
      headers: {
        'Content-Type': 'multipart/form-data',
      },
    });
    return response.data;
  },

  getPhotoUrl(photoId: string): string {
    return `${API_BASE_URL}/api/photos/${photoId}`;
  },
};
