class Feedback {
  final String id;
  final String userId;
  final String courseId;
  final String content;
  final double rating;
  final String dateCreate;

  Feedback({
    required this.id,
    required this.userId,
    required this.courseId,
    required this.content,
    required this.rating,
    required this.dateCreate,
  });

  factory Feedback.fromJson(Map<String, dynamic> json) {
    return Feedback(
      id: json['id'],
      userId: json['userId'],
      courseId: json['courseId'],
      content: json['content'],
      rating: (json['rating'] ?? 0).toDouble(),
      dateCreate: json['dateCreate'],
    );
  }

  Map<String, dynamic> toJson() {
    return {
      'id': id,
      'userId': userId,
      'courseId': courseId,
      'content': content,
      'rating': rating,
      'dateCreate': dateCreate,
    };
  }
}
