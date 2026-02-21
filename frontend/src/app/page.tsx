'use client';

import { useState, useEffect } from 'react';
import { restaurantService } from '@/services/api';
import { Restaurant } from '@/types';
import Link from 'next/link';

export default function Home() {
  const [restaurants, setRestaurants] = useState<Restaurant[]>([]);
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState<string | null>(null);
  const [page, setPage] = useState(0);
  const [totalPages, setTotalPages] = useState(0);

  useEffect(() => {
    loadRestaurants();
  }, [page]);

  const loadRestaurants = async () => {
    try {
      setLoading(true);
      const data = await restaurantService.getAll(page, 20);
      setRestaurants(data.content);
      setTotalPages(data.totalPages);
      setError(null);
    } catch (err) {
      setError('Failed to load restaurants. Please login first.');
      console.error(err);
    } finally {
      setLoading(false);
    }
  };

  return (
    <div className="min-h-screen bg-gray-50">
      <nav className="bg-white shadow">
        <div className="max-w-7xl mx-auto px-4 sm:px-6 lg:px-8">
          <div className="flex justify-between h-16">
            <div className="flex items-center">
              <h1 className="text-2xl font-bold text-gray-900">Restrudent</h1>
            </div>
            <div className="flex items-center space-x-4">
              <Link
                href="/restaurants/new"
                className="bg-primary-600 text-white px-4 py-2 rounded-md hover:bg-primary-700"
              >
                Add Restaurant
              </Link>
            </div>
          </div>
        </div>
      </nav>

      <main className="max-w-7xl mx-auto px-4 sm:px-6 lg:px-8 py-8">
        <h2 className="text-3xl font-bold text-gray-900 mb-6">Restaurants</h2>

        {loading && (
          <div className="flex justify-center items-center h-64">
            <div className="text-gray-500">Loading...</div>
          </div>
        )}

        {error && (
          <div className="bg-red-50 border border-red-200 text-red-700 px-4 py-3 rounded">
            {error}
          </div>
        )}

        {!loading && !error && (
          <div className="grid grid-cols-1 md:grid-cols-2 lg:grid-cols-3 gap-6">
            {restaurants.map((restaurant) => (
              <div
                key={restaurant.id}
                className="bg-white rounded-lg shadow hover:shadow-lg transition-shadow p-6"
              >
                <h3 className="text-xl font-semibold text-gray-900 mb-2">
                  {restaurant.name}
                </h3>
                <p className="text-gray-600 mb-2">{restaurant.cuisineType}</p>
                <p className="text-gray-500 text-sm mb-4">
                  {restaurant.address.city}, {restaurant.address.state}
                </p>
                {restaurant.averageRating && (
                  <div className="flex items-center mb-4">
                    <span className="text-yellow-500">★</span>
                    <span className="ml-1 text-gray-700">
                      {restaurant.averageRating.toFixed(1)}
                    </span>
                  </div>
                )}
                <Link
                  href={`/restaurants/${restaurant.id}`}
                  className="text-primary-600 hover:text-primary-700 font-medium"
                >
                  View Details →
                </Link>
              </div>
            ))}
          </div>
        )}

        {!loading && !error && restaurants.length === 0 && (
          <div className="text-center text-gray-500 py-12">
            No restaurants found. Add your first restaurant!
          </div>
        )}

        {totalPages > 1 && (
          <div className="flex justify-center items-center space-x-4 mt-8">
            <button
              onClick={() => setPage(p => Math.max(0, p - 1))}
              disabled={page === 0}
              className="px-4 py-2 bg-white border border-gray-300 rounded-md disabled:opacity-50 disabled:cursor-not-allowed hover:bg-gray-50"
            >
              Previous
            </button>
            <span className="text-gray-700">
              Page {page + 1} of {totalPages}
            </span>
            <button
              onClick={() => setPage(p => Math.min(totalPages - 1, p + 1))}
              disabled={page >= totalPages - 1}
              className="px-4 py-2 bg-white border border-gray-300 rounded-md disabled:opacity-50 disabled:cursor-not-allowed hover:bg-gray-50"
            >
              Next
            </button>
          </div>
        )}
      </main>
    </div>
  );
}
