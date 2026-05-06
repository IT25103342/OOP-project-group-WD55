import { useState } from 'react';
import { useNavigate } from 'react-router-dom';
import { updateUserProfile } from '../services/api';
import { useAuth } from '../context/AuthContext';

const Profile = () => {
  const { user, logout, updateUser } = useAuth();
  const navigate = useNavigate();
  const [isEditing, setIsEditing] = useState(false);
  const [formData, setFormData] = useState({
    name: user?.name || '',
    email: user?.email || '',
    password: '',
  });
  const [error, setError] = useState('');
  const [success, setSuccess] = useState('');
  const [loading, setLoading] = useState(false);

  if (!user) {
    navigate('/login');
    return null;
  }

  const handleChange = (e) => {
    setFormData({ ...formData, [e.target.name]: e.target.value });
  };

  const handleSubmit = async (e) => {
    e.preventDefault();
    setError('');
    setSuccess('');
    setLoading(true);

    try {
      const updateData = {
        name: formData.name,
        email: formData.email,
      };
      if (formData.password) {
        updateData.password = formData.password;
      }

      const updatedUser = await updateUserProfile(user.id, updateData);
      updateUser(updatedUser);
      setSuccess('Profile updated successfully!');
      setIsEditing(false);
      setFormData({ ...formData, password: '' });
    } catch (err) {
      setError(err.message);
    } finally {
      setLoading(false);
    }
  };

  const handleLogout = () => {
    logout();
    navigate('/login');
  };

  return (
    <div className="profile-container">
      <h2>My Profile</h2>
      {error && <div className="error-message">{error}</div>}
      {success && <div className="success-message">{success}</div>}

      {!isEditing ? (
        <div>
          <div className="profile-info">
            <p><strong>Name:</strong> {user.name}</p>
            <p><strong>Email:</strong> {user.email}</p>
            <p><strong>Role:</strong> {user.role}</p>
          </div>
          <button className="btn" onClick={() => setIsEditing(true)}>
            Edit Profile
          </button>
          <button className="btn btn-secondary" onClick={handleLogout} style={{ marginTop: '1rem' }}>
            Logout
          </button>
        </div>
      ) : (
        <form onSubmit={handleSubmit}>
          <div className="form-group">
            <label>Name</label>
            <input
              type="text"
              name="name"
              value={formData.name}
              onChange={handleChange}
              required
            />
          </div>
          <div className="form-group">
            <label>Email</label>
            <input
              type="email"
              name="email"
              value={formData.email}
              onChange={handleChange}
              required
            />
          </div>
          <div className="form-group">
            <label>New Password (leave blank to keep current)</label>
            <input
              type="password"
              name="password"
              value={formData.password}
              onChange={handleChange}
            />
          </div>
          <button type="submit" className="btn" disabled={loading}>
            {loading ? 'Saving...' : 'Save Changes'}
          </button>
          <button
            type="button"
            className="btn btn-secondary"
            onClick={() => {
              setIsEditing(false);
              setFormData({ name: user.name, email: user.email, password: '' });
            }}
            style={{ marginTop: '1rem' }}
          >
            Cancel
          </button>
        </form>
      )}
    </div>
  );
};

export default Profile;
