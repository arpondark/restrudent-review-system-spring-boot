'use client';

import { useState, useEffect } from 'react';
import { restaurantService } from '@/lib/api';
import { Restaurant } from '@/types';
import Link from 'next/link';

export default function Home() {
  const [restaurants, setRestaurants] = useState<Restaurant[]>([]);
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState<string | null>(null);
  const [page, setPage] = useState(0);
  const [totalPages, setTotalPages] = useState(0);
  const [searchQuery, setSearchQuery] = useState('');

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
      setError('Failed to load restaurants. Please check if the backend is running and you are authenticated.');
      console.error(err);
    } finally {
      setLoading(false);
    }
  };

  const handleSearch = async (e: React.FormEvent) => {
    e.preventDefault();
    if (!searchQuery.trim()) {
      loadRestaurants();
      return;
    }

    try {
      setLoading(true);
      const data = await restaurantService.search(searchQuery, page, 20);
      setRestaurants(data.content);
      setTotalPages(data.totalPages);
      setError(null);
    } catch (err) {
      setError('Search failed. Please try again.');
      console.error(err);
    } finally {
      setLoading(false);
    }
  };

  return (
    <main className="max-w-7xl mx-auto px-4 sm:px-6 lg:px-8 py-8">
      <div className="flex justify-between items-center mb-8">
        <h2 className="text-3xl font-bold text-gray-900">Restaurants</h2>
        <Link
          href="/restaurants/new"
          className="bg-primary-600 text-white px-6 py-2 rounded-lg hover:bg-primary-700 transition-colors"
        >
          Add Restaurant
        </Link>
      </div>

      <form onSubmit={handleSearch} className="mb-8">
        <div className="flex gap-4">
          <input
            type="text"
            value={searchQuery}
            onChange={(e) => setSearchQuery(e.target.value)}
            placeholder="Search restaurants..."
            className="flex-1 px-4 py-2 border border-gray-300 rounded-lg focus:outline-none focus:ring-2 focus:ring-primary-500"
          />
          <button
            type="submit"
            className="bg-primary-600 text-white px-6 py-2 rounded-lg hover:bg-primary-700 transition-colors"
          >
            Search
          </button>
          {searchQuery && (
            <button
              type="button"
              onClick={() => {
                setSearchQuery('');
                setPage(0);
                loadRestaurants();
              }}
              className="bg-gray-200 text-gray-700 px-6 py-2 rounded-lg hover:bg-gray-300 transition-colors"
            >
              Clear
            </button>
          )}
        </div>
      </form>

      {loading && (
        <div className="flex justify-center items-center h-64">
          <div className="animate-spin rounded-full h-12 w-12 border-b-2 border-primary-600"></div>
        </div>
      )}

      {error && (
        <div className="bg-red-50 border border-red-200 text-red-700 px-4 py-3 rounded-lg mb-6">
          {error}
        </div>
      )}

      {!loading && !error && (
        <div className="grid grid-cols-1 md:grid-cols-2 lg:grid-cols-3 gap-6">
          {restaurants.map((restaurant) => (
            <div
              key={restaurant.id}
              className="bg-white rounded-lg shadow-md hover:shadow-xl transition-shadow p-6"
            >
              {restaurant.photos && restaurant.photos.length > 0 && (
                <div className="mb-4 h-48 bg-gray-200 rounded-lg overflow-hidden">
                  <img
                    src={`http://localhost:8080/api/photos/${restaurant.photos[0].url}`}
                    alt={restaurant.name}
                    className="w-full h-full object-cover"
                    onError={(e) => {
                      e.currentTarget.src = '/placeholder.png';
                    }}
                  />
                </div>
              )}
              <h3 className="text-xl font-semibold text-gray-900 mb-2">
                {restaurant.name}
              </h3>
              <p className="text-gray-600 mb-2">{restaurant.cuisineType}</p>
              <p className="text-gray-500 text-sm mb-4">
                {restaurant.address.city}, {restaurant.address.state}
              </p>
              {restaurant.averageRating !== undefined && restaurant.averageRating > 0 && (
                <div className="flex items-center mb-4">
                  <span className="text-yellow-500 text-xl">★</span>
                  <span className="ml-1 text-gray-700 font-semibold">
                    {restaurant.averageRating.toFixed(1)}
                  </span>
                </div>
              )}
              <Link
                href={`/restaurants/${restaurant.id}`}
                className="text-primary-600 hover:text-primary-700 font-medium inline-flex items-center"
              >
                View Details
                <svg className="w-4 h-4 ml-1" fill="none" stroke="currentColor" viewBox="0 0 24 24">
                  <path strokeLinecap="round" strokeLinejoin="round" strokeWidth={2} d="M9 5l7 7-7 7" />
                </svg>
              </Link>
            </div>
          ))}
        </div>
      )}

      {!loading && !error && restaurants.length === 0 && (
        <div className="text-center text-gray-500 py-12">
          <p className="text-xl mb-4">No restaurants found</p>
          <p className="text-gray-400">Add your first restaurant to get started!</p>
        </div>
      )}

      {totalPages > 1 && (
        <div className="flex justify-center items-center space-x-4 mt-8">
          <button
            onClick={() => setPage(p => Math.max(0, p - 1))}
            disabled={page === 0}
            className="px-4 py-2 bg-white border border-gray-300 rounded-lg disabled:opacity-50 disabled:cursor-not-allowed hover:bg-gray-50 transition-colors"
          >
            Previous
          </button>
          <span className="text-gray-700 font-medium">
            Page {page + 1} of {totalPages}
          </span>
          <button
            onClick={() => setPage(p => Math.min(totalPages - 1, p + 1))}
            disabled={page >= totalPages - 1}
            className="px-4 py-2 bg-white border border-gray-300 rounded-lg disabled:opacity-50 disabled:cursor-not-allowed hover:bg-gray-50 transition-colors"
          >
            Next
          </button>
        </div>
      )}
    </main>
  );
}
