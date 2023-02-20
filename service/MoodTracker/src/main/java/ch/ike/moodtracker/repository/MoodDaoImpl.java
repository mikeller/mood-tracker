package ch.ike.moodtracker.repository;

import java.util.Collection;
import java.util.Date;
import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import ch.ike.moodtracker.model.MoodModel;

@Repository
@Transactional
public class MoodDaoImpl implements MoodDao {

	@Autowired
	private SessionFactory sessionFactory;
	
	public void setSessionFactory(SessionFactory sf){
		this.sessionFactory = sf;
	}

	@Override
	public void addMood(MoodModel mood) {
		Session session = this.sessionFactory.getCurrentSession();
		session.persist(mood);
		System.out.println("Mood saved successfully, Mood Details="+mood);
	}

	@Override
	public void updateMood(MoodModel mood) {
		Session session = this.sessionFactory.getCurrentSession();
		session.update(mood);
		System.out.println("Mood updated successfully, Mood Details="+mood);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<MoodModel> listMoods() {
		Session session = this.sessionFactory.getCurrentSession();
		List<MoodModel> moodsList = session.createQuery("from MoodModel").list();
		for(MoodModel mood : moodsList){
			System.out.println("Mood List::"+mood);
		}
		return moodsList;
	}

	@Override
	public MoodModel getMoodById(int id) {
		Session session = this.sessionFactory.getCurrentSession();		
		MoodModel mood = (MoodModel) session.load(MoodModel.class, new Integer(id));
		System.out.println("Mood loaded successfully, Mood details="+mood);
		return mood;
	}

	@Override
	public void removeMood(int id) {
		Session session = this.sessionFactory.getCurrentSession();
		MoodModel mood = (MoodModel) session.load(MoodModel.class, new Integer(id));
		if(null != mood){
			session.delete(mood);
		}
		System.out.println("Mood deleted successfully, Mood details="+mood);
	}
		
	@SuppressWarnings("unchecked")
	public boolean hasSubmittedMood(String token, Date startTime, Date endTime) {
		Session session = this.sessionFactory.getCurrentSession();
		Query query = session.createQuery("from MoodModel where personal_token = :token and create_time between :start_time and :end_time");
		query.setString("token", token);
		query.setTimestamp("start_time", startTime);
		query.setTimestamp("end_time", endTime);
		List<MoodModel> result = query.list();
		return !result.isEmpty();
	}

	@SuppressWarnings("unchecked")
	public Collection<MoodModel> getAllMoods(Date startTime, Date endTime) {
		Session session = this.sessionFactory.getCurrentSession();
		Query query = session.createQuery("from MoodModel where create_time between :start_time and :end_time");
		query.setTimestamp("start_time", startTime);
		query.setTimestamp("end_time", endTime);
		return query.list();
	}

}
