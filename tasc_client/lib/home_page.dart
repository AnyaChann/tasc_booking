import 'package:flutter/material.dart';
import 'dart:convert';
import 'package:http/http.dart' as http;

class Place {
  final String name;
  final String imageUrl;
  final double rating;

  Place({required this.name, required this.imageUrl, required this.rating});

  factory Place.fromJson(Map<String, dynamic> json) {
    return Place(
      name: json['name'],
      imageUrl: json['imageUrl'],
      rating: (json['rating'] as num).toDouble(),
    );
  }
}

class HomePage extends StatefulWidget {
  const HomePage({Key? key}) : super(key: key);

  @override
  State<HomePage> createState() => _HomePageState();
}

class _HomePageState extends State<HomePage> {
  List<Place> places = [];
  bool isLoading = true;
  int selectedCategory = 2; // 0: Hotels, 1: Flights, 2: All

  @override
  void initState() {
    super.initState();
    fetchPlaces(selectedCategory);
  }

  Future<void> fetchPlaces(int category) async {
    setState(() {
      isLoading = true;
      selectedCategory = category;
    });

    String url;
    if (category == 0) {
      url = 'http://localhost:8080/api/places/category/Hotels';
    } else if (category == 1) {
      url = 'http://localhost:8080/api/places/category/Flights';
    } else {
      url = 'http://localhost:8080/api/places/all';
    }

    final response = await http.get(Uri.parse(url));
    if (response.statusCode == 200) {
      final Map<String, dynamic> jsonBody = json.decode(response.body);
      final data = jsonBody['data'];
      if (data is List) {
        setState(() {
          places = data.map((item) => Place.fromJson(item)).toList();
          isLoading = false;
        });
      } else {
        setState(() {
          places = [];
          isLoading = false;
        });
      }
    } else {
      setState(() {
        isLoading = false;
      });
    }
  }

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      backgroundColor: Color(0xFFF7F7F7),
      body: SafeArea(
        child: SingleChildScrollView(
          child: Padding(
            padding: const EdgeInsets.all(18.0),
            child: Column(
              crossAxisAlignment: CrossAxisAlignment.start,
              children: [
                // Header
                Container(
                  width: double.infinity,
                  padding: const EdgeInsets.all(18),
                  decoration: BoxDecoration(
                    borderRadius: BorderRadius.circular(20),
                    gradient: const LinearGradient(
                      colors: [Color(0xFF8B5CF6), Color(0xFF6D28D9)],
                      begin: Alignment.topLeft,
                      end: Alignment.bottomRight,
                    ),
                  ),
                  child: const Text(
                    'Hi Guy!\nWhere are you going next?',
                    style: TextStyle(
                      color: Colors.white,
                      fontSize: 24,
                      fontWeight: FontWeight.bold,
                    ),
                  ),
                ),
                const SizedBox(height: 20),
                // Search bar
                TextField(
                  decoration: InputDecoration(
                    hintText: 'Search your destination',
                    prefixIcon: Icon(Icons.search),
                    filled: true,
                    fillColor: Colors.white,
                    contentPadding: EdgeInsets.symmetric(vertical: 0, horizontal: 16),
                    border: OutlineInputBorder(
                      borderRadius: BorderRadius.circular(16),
                      borderSide: BorderSide.none,
                    ),
                  ),
                ),
                const SizedBox(height: 20),
                // Category buttons
                Row(
                  mainAxisAlignment: MainAxisAlignment.spaceBetween,
                  children: [
                    Expanded(
                      child: GestureDetector(
                        onTap: () => fetchPlaces(0),
                        child: _CategoryButton(
                          icon: Icons.hotel,
                          label: 'Hotels',
                          color: selectedCategory == 0 ? Color(0xFF8B5CF6).withOpacity(0.2) : Color(0xFFFFE0E0),
                        ),
                      ),
                    ),
                    Expanded(
                      child: GestureDetector(
                        onTap: () => fetchPlaces(1),
                        child: _CategoryButton(
                          icon: Icons.flight,
                          label: 'Flights',
                          color: selectedCategory == 1 ? Color(0xFF8B5CF6).withOpacity(0.2) : Color(0xFFFFE3E3),
                        ),
                      ),
                    ),
                    Expanded(
                      child: GestureDetector(
                        onTap: () => fetchPlaces(2),
                        child: _CategoryButton(
                          icon: Icons.apps,
                          label: 'All',
                          color: selectedCategory == 2 ? Color(0xFF8B5CF6).withOpacity(0.2) : Color(0xFFE0F7FA),
                        ),
                      ),
                    ),
                  ],
                ),
                const SizedBox(height: 24),
                // Popular Destinations
                const Text(
                  'Popular Destinations',
                  style: TextStyle(fontSize: 18, fontWeight: FontWeight.bold),
                ),
                const SizedBox(height: 12),
                SizedBox(
                  height: 210,
                  child: isLoading
                      ? Center(child: CircularProgressIndicator())
                      : ListView.separated(
                          scrollDirection: Axis.horizontal,
                          itemCount: places.length,
                          separatorBuilder: (_, __) => SizedBox(width: 16),
                          itemBuilder: (context, index) {
                            final place = places[index];
                            return _PlaceCard(place: place);
                          },
                        ),
                ),
              ],
            ),
          ),
        ),
      ),
      bottomNavigationBar: BottomNavigationBar(
        items: const [
          BottomNavigationBarItem(icon: Icon(Icons.home), label: 'HOME'),
          BottomNavigationBarItem(icon: Icon(Icons.calendar_today), label: ''),
          BottomNavigationBarItem(icon: Icon(Icons.person), label: ''),
        ],
        currentIndex: 0,
        onTap: (index) {},
      ),
    );
  }
}

class _CategoryButton extends StatelessWidget {
  final IconData icon;
  final String label;
  final Color color;

  const _CategoryButton({
    required this.icon,
    required this.label,
    required this.color,
  });

  @override
  Widget build(BuildContext context) {
    return Container(
      margin: const EdgeInsets.symmetric(horizontal: 4),
      padding: const EdgeInsets.symmetric(vertical: 16),
      decoration: BoxDecoration(
        color: color,
        borderRadius: BorderRadius.circular(16),
      ),
      child: Column(
        children: [
          Icon(icon, size: 32, color: Colors.deepPurple),
          const SizedBox(height: 8),
          Text(label, style: TextStyle(fontWeight: FontWeight.bold)),
        ],
      ),
    );
  }
}

class _PlaceCard extends StatelessWidget {
  final Place place;

  const _PlaceCard({required this.place});

  @override
  Widget build(BuildContext context) {
    return Container(
      width: 150,
      decoration: BoxDecoration(
        color: Colors.white,
        borderRadius: BorderRadius.circular(16),
        boxShadow: [BoxShadow(color: Colors.black12, blurRadius: 6)],
      ),
      child: Column(
        crossAxisAlignment: CrossAxisAlignment.start,
        children: [
          Stack(
            children: [
              ClipRRect(
                borderRadius: BorderRadius.vertical(top: Radius.circular(16)),
                child: Image.network(
                  place.imageUrl,
                  height: 100,
                  width: 150,
                  fit: BoxFit.cover,
                  errorBuilder: (_, __, ___) => Container(
                    height: 100,
                    width: 150,
                    color: Colors.grey[300],
                    child: Icon(Icons.image, size: 40),
                  ),
                ),
              ),
              Positioned(
                top: 8,
                right: 8,
                child: Icon(Icons.favorite, color: Colors.redAccent),
              ),
            ],
          ),
          Padding(
            padding: const EdgeInsets.all(8.0),
            child: Text(
              place.name,
              style: TextStyle(fontWeight: FontWeight.bold, fontSize: 16),
              maxLines: 1,
              overflow: TextOverflow.ellipsis,
            ),
          ),
          Padding(
            padding: const EdgeInsets.symmetric(horizontal: 8.0),
            child: Row(
              children: [
                Icon(Icons.star, color: Colors.amber, size: 18),
                SizedBox(width: 4),
                Text(place.rating.toStringAsFixed(1)),
              ],
            ),
          ),
        ],
      ),
    );
  }
}