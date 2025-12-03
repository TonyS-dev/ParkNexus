import { api } from './api';

export const adminService = {
  getUsers: () => api.get('/admin/users'),
  updateUser: (id: string, data: any) => api.post(`/admin/users/${id}`, data),
};

