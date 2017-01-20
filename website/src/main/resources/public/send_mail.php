<?
$mail_to = strip_tags(trim($_POST['mailto']));


$name = strip_tags(trim($_POST['name']));
$email = strip_tags(trim($_POST['email']));
$subject = strip_tags(trim($_POST['subject']));
if ($subject != '') $mail_subject = $subject;
else $mail_subject = 'Contact form submited';
$department = strip_tags(trim($_POST['department']));
$text = strip_tags(trim($_POST['text']));

$message = 'Contact form was submited' . "\r\n\r\n";
$message .= 'Name: ' . $name . "\r\n";
$message .= 'Email: ' . $email . "\r\n";
$message .= 'Department: ' . $department . "\r\n";
$message .= 'Message: ' . $text . "\r\n";

mail($mail_to, $mail_subject, $message);
?>