import com.projectprovip.h1eu.giasu.presentation.profile.model.CoursePaymentModel

data class CoursePayment (
    val code: String,
    val courseId: String,
    val courseTitle: String,
    val paymentId: String,
    val paymentStatus: String
)

fun CoursePayment.toCoursePaymentModel() = CoursePaymentModel(
    code, courseId, courseTitle, paymentId, paymentStatus
)